package com.mobileteam.firebasehistory.interactor

import com.google.firebase.database.Query


data class LoadHistoryFormFirebaseParams(val sourceQuery: Query,
                                         val oldestItemKey: String,
                                         val historyItemsLoadingStep: Int)