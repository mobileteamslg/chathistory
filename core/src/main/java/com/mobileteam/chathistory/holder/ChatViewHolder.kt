package com.mobileteam.chathistory.holder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.mobileteam.chathistory.adapter.ChatHistoryAdapter.ChatItem

abstract class ChatViewHolder(parent: ViewGroup, @LayoutRes layoutRes: Int) : RecyclerView.ViewHolder(buildView(parent, layoutRes)) {

    interface LayoutParams

    open fun <ItemType : ChatItem> onBindItem(item: ItemType, layoutParams: LayoutParams?) {
        Log.e(javaClass.simpleName, "onBindItem: undefined item type. position=$adapterPosition, item=$item")
    }

    companion object {
        private fun buildView(parent: ViewGroup, @LayoutRes layoutRes: Int): View =
            LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    }

}