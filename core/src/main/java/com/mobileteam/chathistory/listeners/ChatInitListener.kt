package com.mobileteam.chathistory.listeners

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class ChatInitListener(
    private val onDataReceived: (data: DataSnapshot) -> Unit,
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
        query.removeEventListener(this@ChatInitListener)
    }

    fun attachListener(initialQuery: Query, initSize: Int) {
        detachListener()
        query = initialQuery.ref.limitToLast(initSize)
        query?.addListenerForSingleValueEvent(this@ChatInitListener)
        Log.d(javaClass.simpleName, "attach listener: $reference")
    }
}