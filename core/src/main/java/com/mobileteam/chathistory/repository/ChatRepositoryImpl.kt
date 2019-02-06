package com.mobileteam.chathistory.repository

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.interactor.InitialQueryParams
import com.mobileteam.chathistory.interactor.ListenToNewMessagesParams
import com.mobileteam.chathistory.listeners.livedata.ChatInitLiveData
import com.mobileteam.chathistory.listeners.livedata.ChatNewItemLiveData
import com.mobileteam.chathistory.util.Response

class ChatRepositoryImpl : ChatRepository {

    override fun createInitialQueryLiveData(params: InitialQueryParams): LiveData<Response<ChatError, DataSnapshot>> = with(params) {
        ChatInitLiveData(initialQuery, initialSize)
    }

    override fun createNewMessagesLiveData(params: ListenToNewMessagesParams): LiveData<Response<ChatError, DataSnapshot>> = with(params) {
        ChatNewItemLiveData(sourceQuery, newestItemKey)
    }
}