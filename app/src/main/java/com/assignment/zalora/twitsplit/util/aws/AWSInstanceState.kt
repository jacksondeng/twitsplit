package com.assignment.zalora.twitsplit.util.aws

sealed class AWSInstanceState {
    object Initialized : AWSInstanceState()
    object NotInitialized : AWSInstanceState()
}