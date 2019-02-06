package com.mobileteam.chathistory.viewmodel

import com.google.firebase.database.DataSnapshot

class KeysStore {

    fun updateMessagesKeys(dataSnapshotsList: Iterable<DataSnapshot>) {
        dataSnapshotsList.forEach { itemDataSnapshot ->
            itemDataSnapshot.key?.let {
                _messagesKeys.add(it)
            }
        }
    }

    fun updateMessagesKeys(key: String) {
        _messagesKeys.add(key)
    }

    fun updateHistoryLoadedKeys(key: String) {
        _keysLoadHistory.add(key)
    }

    fun updateOldestItemKey(key: String) {
        _oldestItemKey = key
    }

    fun updateNewestItemKey(key: String) {
        _newestItemKey = key
    }

    val messagesKeys: Set<String>
        get() = _messagesKeys

    val keysLoadHistory: Set<String>
        get() = _keysLoadHistory

    val oldestItemKey: String
        get() = _oldestItemKey

    val newestItemKey: String
        get() = _newestItemKey

    private val _messagesKeys = hashSetOf<String>()
    private val _keysLoadHistory = hashSetOf<String>()
    private var _oldestItemKey: String = ""
    private var _newestItemKey: String = ""
}