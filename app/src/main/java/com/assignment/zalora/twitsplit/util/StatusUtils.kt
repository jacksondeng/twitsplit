package com.assignment.zalora.twitsplit.util

import android.arch.lifecycle.LifecycleOwner


interface StatusUtils {

    fun showLoading()

    fun showError(throwable: Throwable, tryAgainAction: (() -> Unit)?)

    fun showError(throwable: Throwable)

    fun hideLoading()

}