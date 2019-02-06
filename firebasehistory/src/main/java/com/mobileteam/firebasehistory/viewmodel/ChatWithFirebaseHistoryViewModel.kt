package com.mobileteam.firebasehistory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.mobileteam.chathistory.util.firstKey
import com.mobileteam.chathistory.viewmodel.BaseChatViewModel
import com.mobileteam.firebasehistory.interactor.LoadHistoryFormFirebaseParams
import com.mobileteam.firebasehistory.interactor.LoadItemsFromFbHistory

class ChatWithFirebaseHistoryViewModel(private val loadItemsFromFbHistory: LoadItemsFromFbHistory = LoadItemsFromFbHistory()) : BaseChatViewModel() {

    fun loadItemsFromHistory(sourceQuery: Query, historyItemsLoadingStep: Int) = with(keysStore) {
        if (oldestItemKey in keysLoadHistory) return
        updateHistoryLoadedKeys(oldestItemKey)
        val fbHistoryLiveData = loadItemsFromFbHistory(LoadHistoryFormFirebaseParams(sourceQuery, oldestItemKey, historyItemsLoadingStep))
        historyMediatorLiveData.addSource(fbHistoryLiveData) {
            historyMediatorLiveData.value = it.successOrNull()?.let(::handleHistory)
        }
        listenToErrors(fbHistoryLiveData)

    }

    private fun handleHistory(historyItemsDataSnapshot: DataSnapshot): List<DataSnapshot>? = with(keysStore) {
        val validHistoryItemsSnapshotsList = historyItemsDataSnapshot.children.filterNot { it.key in messagesKeys }
        if (validHistoryItemsSnapshotsList.isEmpty()) return@with null
        validHistoryItemsSnapshotsList.apply {
            updateMessagesKeys(this)
            firstKey?.let { updateOldestItemKey(it) }
        }
    }

    val historyLiveData: LiveData<List<DataSnapshot>>
        get() = historyMediatorLiveData

    private val historyMediatorLiveData = MediatorLiveData<List<DataSnapshot>>()
}