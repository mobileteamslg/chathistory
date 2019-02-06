package com.mobileteam.chathistory.listeners.livedata

import androidx.lifecycle.MutableLiveData

abstract class BaseChatLiveData<DataType : Any> : MutableLiveData<DataType>() {

    abstract fun detachListener()

    protected abstract fun attachListener()

    fun reattachListener() {
        if (hasActiveObservers()) {
            detachListener()
            attachListener()
        }
    }
}