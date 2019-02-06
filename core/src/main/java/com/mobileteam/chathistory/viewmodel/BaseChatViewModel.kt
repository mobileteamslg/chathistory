package com.mobileteam.chathistory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.interactor.InitialQueryParams
import com.mobileteam.chathistory.interactor.ListenToNewMessages
import com.mobileteam.chathistory.interactor.ListenToNewMessagesParams
import com.mobileteam.chathistory.interactor.PerformInitialQuery
import com.mobileteam.chathistory.util.Response
import com.mobileteam.chathistory.util.firstKey
import com.mobileteam.chathistory.util.lastKey

abstract class BaseChatViewModel(private val performInitialQuery: PerformInitialQuery = PerformInitialQuery(),
                                 private val listenToNewMessages: ListenToNewMessages = ListenToNewMessages(),
                                 protected val keysStore: KeysStore = KeysStore()) : ViewModel() {

    fun performInitialQuery(sourceQuery: Query, initialSize: Int) {
        val chatInitLiveData = performInitialQuery(InitialQueryParams(sourceQuery, initialSize))
        initialQueryMediatorLiveData.addSource(chatInitLiveData) {
            initialQueryMediatorLiveData.value = it.successOrNull()?.let(::handleInitialData)
        }
        listenToErrors(chatInitLiveData)
    }

    fun listenToNewMessages(sourceQuery: Query) {
        val chatNewMessagesLiveData = listenToNewMessages(ListenToNewMessagesParams(sourceQuery, keysStore::newestItemKey))
        newMessagesMediatorLiveData.addSource(chatNewMessagesLiveData) {
            newMessagesMediatorLiveData.value = it.successOrNull()?.let(::handleNewMessage)
        }
        listenToErrors(chatNewMessagesLiveData)
    }

    protected fun <DataType> listenToErrors(targetLiveData: LiveData<Response<ChatError, DataType>>) {
        failuresMediatorLiveData.addSource(targetLiveData, createFailuresObserver(failuresMediatorLiveData))
    }

    private fun handleInitialData(initialItemsSnapshots: DataSnapshot): List<DataSnapshot>? {
        val initialItemsSnapshotsList = initialItemsSnapshots.children.toList()
        return if (initialItemsSnapshotsList.isNotEmpty()) {
            initialItemsSnapshotsList.apply {
                firstKey?.let { keysStore.updateOldestItemKey(it) }
                lastKey?.let { keysStore.updateNewestItemKey(it) }
                keysStore.updateMessagesKeys(this)
            }
        } else {
            handleError(ChatError.INITIAL_CALL_EMPTY_DATA)
            null
        }
    }

    private fun handleNewMessage(newMessage: DataSnapshot) = newMessage.key?.let { newMessageKey ->
        with(keysStore) {
            if (newMessageKey in messagesKeys) return null
            updateNewestItemKey(newMessageKey)
            updateMessagesKeys(newMessageKey)
            newMessage
        }
    }

    private fun handleError(chatError: ChatError) {
        failuresMediatorLiveData.value = chatError
    }

    private fun <SuccessType> createFailuresObserver(failuresMediatorLiveData: MediatorLiveData<ChatError>): Observer<Response<ChatError, SuccessType>> = Observer { response ->
        failuresMediatorLiveData.value = response.failureOrNull()
    }

    val initialQueryLiveData: LiveData<List<DataSnapshot>>
        get() = initialQueryMediatorLiveData

    val newMessagesLiveData: LiveData<DataSnapshot>
        get() = newMessagesMediatorLiveData

    val failuresLiveData: LiveData<ChatError>
        get() = failuresMediatorLiveData

    private val initialQueryMediatorLiveData = MediatorLiveData<List<DataSnapshot>>()
    private val newMessagesMediatorLiveData = MediatorLiveData<DataSnapshot>()
    private val failuresMediatorLiveData = MediatorLiveData<ChatError>()
}