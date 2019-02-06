package com.mobileteam.chathistory.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.firebase.database.DataSnapshot

val <T : Class<*>> T.itemType: Int
    get() = simpleName.hashCode()

val Iterable<DataSnapshot>.firstKey: String?
    get() = firstOrNull()?.key

val Iterable<DataSnapshot>.lastKey: String?
    get() = lastOrNull()?.key

fun <T> Iterable<T>.isNotEmpty(): Boolean =
    iterator().hasNext()

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, observer: Observer<T>) =
    liveData.observe(this, observer)