package com.mobileteam.chathistory.interactor

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.repository.ChatRepository
import com.mobileteam.chathistory.repository.ChatRepositoryImpl
import com.mobileteam.chathistory.util.Response

class PerformInitialQuery(private val chatRepository: ChatRepository = ChatRepositoryImpl()) {

    operator fun invoke(params: InitialQueryParams): LiveData<Response<ChatError, DataSnapshot>> =
        chatRepository.createInitialQueryLiveData(params)

}