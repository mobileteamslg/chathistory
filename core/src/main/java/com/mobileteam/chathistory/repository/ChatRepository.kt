package com.mobileteam.chathistory.repository

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.interactor.InitialQueryParams
import com.mobileteam.chathistory.interactor.ListenToNewMessagesParams
import com.mobileteam.chathistory.util.Response

interface ChatRepository {

    fun createInitialQueryLiveData(params: InitialQueryParams): LiveData<Response<ChatError, DataSnapshot>>

    fun createNewMessagesLiveData(params: ListenToNewMessagesParams): LiveData<Response<ChatError, DataSnapshot>>
}