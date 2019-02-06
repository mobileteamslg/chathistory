package com.mobileteam.chathistory.history

import androidx.recyclerview.widget.RecyclerView

data class HistoryParams(
    val customHistoryLoadingListener: RecyclerView.OnScrollListener? = null,
    val startHistoryLoadingThreshold: Int = 3,
    val historyItemsLoadingStep: Int = 10
)