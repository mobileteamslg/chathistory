package com.mobileteam.chathistory.interactor

import com.google.firebase.database.Query

data class InitialQueryParams(val initialQuery: Query,
                              val initialSize: Int)

data class ListenToNewMessagesParams(val sourceQuery: Query,
                                     val newestItemKey: () -> String)