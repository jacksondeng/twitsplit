package com.assignment.zalora.twitsplit.util

import android.arch.lifecycle.MutableLiveData

class LiveDataExt{
    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

    val liveData = MutableLiveData<String>().default("Initial value!")
}