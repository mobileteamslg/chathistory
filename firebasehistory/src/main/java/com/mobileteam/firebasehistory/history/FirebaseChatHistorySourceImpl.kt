package com.mobileteam.firebasehistory.history

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.util.Response
import com.mobileteam.firebasehistory.interactor.LoadHistoryFormFirebaseParams
import com.mobileteam.firebasehistory.listeners.ChatFbHistoryLiveData

class FirebaseChatHistorySourceImpl : FirebaseChatHistorySource {

    override fun loadHistory(params: LoadHistoryFormFirebaseParams): LiveData<Response<ChatError, DataSnapshot>> = with(params) {
        ChatFbHistoryLiveData(sourceQuery, historyItemsLoadingStep, oldestItemKey)
    }
}