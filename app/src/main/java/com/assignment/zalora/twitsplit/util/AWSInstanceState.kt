package com.assignment.zalora.twitsplit.util

sealed class AWSInstanceState {
    object Initialized : AWSInstanceState()
    object NotInitialized : AWSInstanceState()
}