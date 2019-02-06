package com.mobileteam.chathistory.listeners

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query

class ChatNewItemListener(private val onItemAdded: (DataSnapshot) -> Unit,
                          private val onErrorHandled: () -> Unit) : BaseChatListener(), ChildEventListener {

    /** ChildEventListener */
    override fun onCancelled(error: DatabaseError) {
        Log.e(javaClass.simpleName, "error = ${error.message}")
        onErrorHandled.invoke()
    }

    /** ChildEventListener */
    override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) =
        onItemAdded.invoke(dataSnapshot)

    /** ChildEventListener */
    override fun onChildMoved(p0: DataSnapshot, p1: String?) = Unit

    /** ChildEventListener */
    override fun onChildChanged(p0: DataSnapshot, p1: String?) = Unit

    /** ChildEventListener */
    override fun onChildRemoved(p0: DataSnapshot) = Unit

    /** BaseChatListener. Note: modifier of the fun is protected */
    override fun performDetaching(query: Query) {
        query.removeEventListener(this@ChatNewItemListener)
    }

    fun attachListener(sourceQuery: Query, newestItemKey: String) {
        detachListener()
        query = sourceQuery.ref.orderByKey().startAt(newestItemKey)
        query?.addChildEventListener(this@ChatNewItemListener)
        Log.d(javaClass.simpleName, "attach listener: $reference")
    }
}