package com.mobileteam.chathistory.adapter

import androidx.lifecycle.LifecycleOwner
import com.google.firebase.database.Query
import com.mobileteam.chathistory.history.HistoryParams

class ChatHistoryAdapterParams(val owner: LifecycleOwner,
                               val sourceQuery: Query,
                               val initialSize: Int = 10,
                               val historyParams: HistoryParams = HistoryParams()
)