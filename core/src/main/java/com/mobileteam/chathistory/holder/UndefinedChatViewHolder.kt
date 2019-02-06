package com.mobileteam.chathistory.holder

import android.util.Log
import android.view.ViewGroup
import com.mobileteam.chathistory.R
import com.mobileteam.chathistory.adapter.ChatHistoryAdapter.ChatItem
import kotlinx.android.synthetic.main.chat_item_undefined.view.*

class UndefinedChatViewHolder(parent: ViewGroup) : ChatViewHolder(parent, R.layout.chat_item_undefined) {

    override fun <ItemType : ChatItem> onBindItem(item: ItemType, layoutParams: LayoutParams?) = with(itemView) {
        val undefinedText = "Undefined holder for item: ${item.javaClass.simpleName}"
        textView.text = undefinedText
        Log.w(javaClass.simpleName, undefinedText)
        Unit
    }
}