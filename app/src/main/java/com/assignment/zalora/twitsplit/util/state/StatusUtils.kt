package com.assignment.zalora.twitsplit.util.state


interface StatusUtils {
    fun showLoading()
    fun showError(err : String)
    fun hideLoading()
}