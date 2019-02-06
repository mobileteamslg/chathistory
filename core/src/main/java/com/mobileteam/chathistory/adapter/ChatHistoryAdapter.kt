package com.mobileteam.chathistory.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.mobileteam.chathistory.ChatError
import com.mobileteam.chathistory.history.HistoryLoadingListener
import com.mobileteam.chathistory.holder.ChatViewHolder
import com.mobileteam.chathistory.holder.ChatViewHolderBuilder
import com.mobileteam.chathistory.holder.UndefinedChatViewHolder
import com.mobileteam.chathistory.holder.builder
import com.mobileteam.chathistory.listeners.livedata.ItemsObserver
import com.mobileteam.chathistory.util.itemType
import com.mobileteam.chathistory.util.observe
import com.mobileteam.chathistory.viewmodel.BaseChatViewModel

abstract class ChatHistoryAdapter<ChatViewModelType : BaseChatViewModel>(chatHistoryAdapterParams: ChatHistoryAdapterParams,
                                                                         protected val chatViewModel: ChatViewModelType) : RecyclerView.Adapter<ChatViewHolder>(),
    LifecycleObserver {

    init {
        with(chatHistoryAdapterParams) {
            initAdapterByParams(this)
            owner.lifecycle.addObserver(this@ChatHistoryAdapter)
            performInitialQuery(initialSize)
        }
    }

    protected abstract fun handleInitialItems(initialItemsSnapshots: List<DataSnapshot>)

    protected abstract fun handleNewItem(itemDataSnapshot: DataSnapshot)

    protected abstract fun handleError(chatError: ChatError)

    protected abstract fun listenToHistoryUpdates()

    protected abstract fun loadItemsFromHistory()

    protected abstract val holderBuilders: List<ChatViewHolderBuilder>

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(historyLoadingListener)
        attachedRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeOnScrollListener(historyLoadingListener)
        attachedRecyclerView = null
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].javaClass.itemType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder =
        extractBuilder(viewType).viewHolder(parent)

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = items[position]
        with(extractBuilder(holder.itemViewType, item)) {
            if (isClickable) holder.itemView.setOnClickListener { onItemClickListener?.onItemClicked(item) }
            holder.onBindItem(item, layoutParams)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cleanup(source: LifecycleOwner) {
        source.lifecycle.removeObserver(this@ChatHistoryAdapter)
        owner = null
    }

    private fun initAdapterByParams(chatHistoryAdapterParams: ChatHistoryAdapterParams) {
        initialDataObserver = ItemsObserver(::onInitialDataReceived)
        failuresDataObserver = ItemsObserver(::handleError)
        sourceQuery = chatHistoryAdapterParams.sourceQuery
        owner = chatHistoryAdapterParams.owner
        with(chatHistoryAdapterParams.historyParams) {
            historyLoadingListener = customHistoryLoadingListener
                ?: HistoryLoadingListener(startHistoryLoadingThreshold, ::loadItemsFromHistory)
        }
    }

    private fun performInitialQuery(initialSize: Int) = with(chatViewModel) {
        owner?.observe(initialQueryLiveData, initialDataObserver)
        owner?.observe(failuresLiveData, failuresDataObserver)
        performInitialQuery(sourceQuery, initialSize)
    }

    private fun onInitialDataReceived(initialItemsSnapshots: List<DataSnapshot>) {
        chatViewModel.initialQueryLiveData.removeObserver(initialDataObserver)
        handleInitialItems(initialItemsSnapshots)
        listenToNewItems()
        listenToHistoryUpdates()
    }

    private fun listenToNewItems() = with(chatViewModel) {
        owner?.observe(newMessagesLiveData, newItemObserver)
        listenToNewMessages(sourceQuery)
    }

    private fun extractBuilder(type: Int, item: ChatItem? = null) = holderRegistersMap[type]
        ?: defaultBuilder.apply { item?.let { Log.e(this@ChatHistoryAdapter.javaClass.simpleName, "Undefined holder layout. Item=$it") } }

    protected var owner: LifecycleOwner? = null
    protected var attachedRecyclerView: RecyclerView? = null

    protected lateinit var sourceQuery: Query

    protected val items = mutableListOf<ChatItem>()

    private var onItemClickListener: OnItemClickListener? = null

    private lateinit var initialDataObserver: ItemsObserver<List<DataSnapshot>>
    private lateinit var failuresDataObserver: ItemsObserver<ChatError>
    private lateinit var historyLoadingListener: RecyclerView.OnScrollListener

    private val holderRegistersMap: Map<Int, ChatViewHolderBuilder> by lazy {
        holderBuilders.associate { it.itemType to it }
    }

    private val defaultBuilder = builder(ChatItem::class, ::UndefinedChatViewHolder)
    private val newItemObserver = ItemsObserver(::handleNewItem)

    interface ChatItem

    interface OnItemClickListener {
        fun onItemClicked(item: ChatItem)
    }
}