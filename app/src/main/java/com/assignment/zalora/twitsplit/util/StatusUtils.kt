package com.assignment.zalora.twitsplit.util

import android.arch.lifecycle.LifecycleOwner


interface StatusUtils {

    fun showLoading()

    fun showError(err : String)

    fun hideLoading()

}