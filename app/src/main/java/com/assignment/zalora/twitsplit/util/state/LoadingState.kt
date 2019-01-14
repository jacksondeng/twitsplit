package com.assignment.zalora.twitsplit.util.state

sealed class LoadingState {
    object Posting : LoadingState()
    object Deleting : LoadingState()
    object Loading : LoadingState()
    object Success : LoadingState()
    object Failed : LoadingState()
    data class Error(val errorCode: ErrorCode) : LoadingState()
}