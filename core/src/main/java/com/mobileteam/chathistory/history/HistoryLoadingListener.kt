package com.mobileteam.chathistory.history

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class HistoryLoadingListener(private val threshold: Int, private val onStartLoading: () -> Unit) : RecyclerView.OnScrollListener() {
    @Suppress("UNCHECKED_CAST")
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        (recyclerView.layoutManager as? LinearLayoutManager)?.let {
            if (it.findFirstVisibleItemPosition() < threshold) {
                onStartLoading.invoke()
            }
        }
    }
}