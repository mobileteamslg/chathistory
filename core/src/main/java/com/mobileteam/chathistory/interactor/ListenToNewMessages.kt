package com.mobileteam.chathistory.interactor

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.repository.ChatRepository
import com.mobileteam.chathistory.repository.ChatRepositoryImpl
import com.mobileteam.chathistory.util.Response

class ListenToNewMessages(private val chatRepository: ChatRepository = ChatRepositoryImpl()) {

    operator fun invoke(params: ListenToNewMessagesParams): LiveData<Response<ChatError, DataSnapshot>> =
        chatRepository.createNewMessagesLiveData(params)

}