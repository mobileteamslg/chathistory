package com.mobileteam.firebasehistory.listeners

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.mobileteam.chathistory.listeners.BaseChatListener

class ChatFbHistoryListener(
    private val onDataReceived: (itemsDataSnapshot: DataSnapshot) -> Unit,
    private val onErrorHandled: () -> Unit
) : BaseChatListener(), ValueEventListener {


    /** ValueEventListener */
    override fun onCancelled(error: DatabaseError) {
        Log.e(javaClass.simpleName, "error = ${error.message}")
        onErrorHandled.invoke()
    }

    /** ValueEventListener */
    override fun onDataChange(dataShaphot: DataSnapshot) {
        onDataReceived.invoke(dataShaphot)
    }

    /** BaseChatListener. Note: modifier of the fun is protected */
    override fun performDetaching(query: Query) {
        query.removeEventListener(this@ChatFbHistoryListener)
    }

    fun attachListener(sourceQuery: Query, oldestKey: String, step: Int) {
        detachListener()
        query = sourceQuery.ref.orderByKey().endAt(oldestKey).limitToLast(step)
        query?.addListenerForSingleValueEvent(this@ChatFbHistoryListener)
        Log.d(javaClass.simpleName, "attach listener: $reference")
    }
}