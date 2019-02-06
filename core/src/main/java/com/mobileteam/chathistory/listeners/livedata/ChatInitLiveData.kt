package com.mobileteam.chathistory.listeners.livedata

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.listeners.ChatInitListener
import com.mobileteam.chathistory.util.Response

class ChatInitLiveData(private val initialQuery: Query,
                       private val initialSize: Int) : BaseChatLiveData<Response<ChatError, DataSnapshot>>() {

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
        chatInitListener.detachListener()
    }

    /** BaseChatLiveData. Note: modifier of the fun is protected */
    override fun attachListener() {
        chatInitListener.attachListener(initialQuery, initialSize)
    }

    private fun onDataReceived(itemsSnapshots: DataSnapshot) {
        value = Response.Success(itemsSnapshots)
        handleResponse()
    }

    private fun onErrorHandled() {
        value = Response.Failure(ChatError.INITIAL_CALL_ERROR)
        handleResponse()
    }

    private fun handleResponse() {
        isItemsLoaded = true
        detachListener()
    }

    private var isItemsLoaded: Boolean = false

    private val chatInitListener = ChatInitListener(::onDataReceived, ::onErrorHandled)
}