package com.mobileteam.chathistory.holder

import android.view.ViewGroup
import com.mobileteam.chathistory.util.itemType
import kotlin.reflect.KClass

class ChatViewHolderBuilder(itemsClass: KClass<out ChatItem>,
                            val viewHolder: (parent: ViewGroup) -> ChatViewHolder,
                            val isClickable: Boolean = false,
                            val layoutParams: ChatViewHolder.LayoutParams? = null,
                            val itemType: Int = itemsClass.java.itemType)

typealias builder = ChatViewHolderBuilder