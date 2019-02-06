package com.mobileteam.firebasehistory.interactor

import com.mobileteam.firebasehistory.history.FirebaseChatHistorySource
import com.mobileteam.firebasehistory.history.FirebaseChatHistorySourceImpl

class LoadItemsFromFbHistory(private val firebaseChatHistorySource: FirebaseChatHistorySource = FirebaseChatHistorySourceImpl()) {

    operator fun invoke(params: LoadHistoryFormFirebaseParams) =
        firebaseChatHistorySource.loadHistory(params)

}