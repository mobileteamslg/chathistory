package com.mobileteam.firebasehistory.adapter

import com.google.firebase.database.DataSnapshot
import com.mobileteam.chathistory.adapter.ChatHistoryAdapter
import com.mobileteam.chathistory.adapter.ChatHistoryAdapterParams
import com.mobileteam.chathistory.listeners.livedata.ItemsObserver
import com.mobileteam.chathistory.util.observe
import com.mobileteam.firebasehistory.viewmodel.ChatWithFirebaseHistoryViewModel

abstract class ChatFirebaseHistoryAdapter(params: ChatHistoryAdapterParams, chatViewModel: ChatWithFirebaseHistoryViewModel = ChatWithFirebaseHistoryViewModel())
    : ChatHistoryAdapter<ChatWithFirebaseHistoryViewModel>(params, chatViewModel) {

    protected abstract fun handleHistory(historyItemsSnapshotsList: List<DataSnapshot>)

    /** Note: modifier of the fun is protected */
    override fun listenToHistoryUpdates() {
        owner?.observe(chatViewModel.historyLiveData, historyUpdatesObserver)
    }

    /** Note: modifier of the fun is protected */
    override fun loadItemsFromHistory() {
        chatViewModel.loadItemsFromHistory(sourceQuery, historyItemsLoadingStep)
    }

    private val historyItemsLoadingStep: Int = params.historyParams.historyItemsLoadingStep
    private val historyUpdatesObserver = ItemsObserver(::handleHistory)

}