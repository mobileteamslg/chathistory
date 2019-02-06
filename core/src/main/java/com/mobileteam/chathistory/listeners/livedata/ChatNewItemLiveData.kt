package com.mobileteam.chathistory.listeners.livedata

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.ChatError.*
import com.mobileteam.chathistory.listeners.ChatNewItemListener
import com.mobileteam.chathistory.util.Response
import com.mobileteam.chathistory.util.Response.*

class ChatNewItemLiveData(private val sourceQuery: Query,
                          private val newestItemKey: () -> String) : BaseChatLiveData<Response<ChatError, DataSnapshot>>() {

    /** LiveData */
    override fun onActive() {
        attachListener()
    }

    /** LiveData */
    override fun onInactive() {
        detachListener()
    }

    /** BaseChatLiveData */
    override fun detachListener() {
        chatNewItemListener.detachListener()
    }

    /** BaseChatLiveData. Note: modifier of the fun is protected */
    override fun attachListener() {
        chatNewItemListener.attachListener(sourceQuery, newestItemKey())
    }

    private fun onItemAdded(dataSnapshot: DataSnapshot) {
        value = Success(dataSnapshot)
    }

    private fun onErrorHandled() {
        value = Failure(RECEIVE_NEW_ITEM_ERROR)
    }

    private val chatNewItemListener = ChatNewItemListener(::onItemAdded, ::onErrorHandled)
}