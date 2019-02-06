package com.mobileteam.chathistory.listeners

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

abstract class BaseChatListener {

    protected abstract fun performDetaching(query: Query)

    fun detachListener() = query?.let {
        performDetaching(it)
        Log.d(javaClass.simpleName, "detachListener: $reference")
    }

    protected val reference: DatabaseReference?
        get() = query?.ref

    protected var query: Query? = null
}