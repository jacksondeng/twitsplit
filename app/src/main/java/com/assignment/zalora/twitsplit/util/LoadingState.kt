package com.assignment.zalora.twitsplit.util

sealed class LoadingState {
    object Posting : LoadingState()
    object Loading : LoadingState()
    object Success : LoadingState()
    object Failed : LoadingState()
    object Error : LoadingState()
}