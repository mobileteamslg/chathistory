package com.mobileteam.chathistory.listeners.livedata

import androidx.lifecycle.Observer

class ItemsObserver<T>(private val onItemsUpdated: (T) -> Unit) : Observer<T> {

    override fun onChanged(itemsUpdate: T) {
        itemsUpdate?.let { onItemsUpdated(it) }
    }
}