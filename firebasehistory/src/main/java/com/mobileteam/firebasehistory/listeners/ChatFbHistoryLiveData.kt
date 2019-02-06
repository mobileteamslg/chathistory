package com.mobileteam.firebasehistory.listeners

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.ChatError.*
import com.mobileteam.chathistory.listeners.livedata.BaseChatLiveData
import com.mobileteam.chathistory.util.Response
import com.mobileteam.chathistory.util.Response.*

class ChatFbHistoryLiveData(private val sourceQuery: Query,
                            private val itemsLoadingStep: Int,
                            private val oldestKey: String) : BaseChatLiveData<Response<ChatError, DataSnapshot>>() {

    /** LiveData */
    override fun onActive() {
        if (!isItemsLoaded) {
            attachListener()
        }
    }

    /** LiveData */
    override fun onInactive() {
        detachListener()
    }

    /** BaseChatLiveData */
    override fun detachListener() {
        chatFbHistoryListener.detachListener()
    }

    /** BaseChatLiveData. Note: modifier of the fun is protected */
    override fun attachListener() {
        chatFbHistoryListener.attachListener(sourceQuery, oldestKey, itemsLoadingStep)
    }

    private fun onDataReceived(itemsDataSnapshot: DataSnapshot) {
        value = Success(itemsDataSnapshot)
        handleResponse()
    }

    private fun onErrorHandled() {
        value = Failure(HISTORY_LOADING_ERROR)
        handleResponse()
    }

    private fun handleResponse() {
        isItemsLoaded = true
        detachListener()
    }

    private var isItemsLoaded: Boolean = false

    private val chatFbHistoryListener = ChatFbHistoryListener(::onDataReceived, ::onErrorHandled)
}