package com.assignment.zalora.twitsplit.util.state

sealed class ErrorCode{
    object NetworkError : ErrorCode()
    object InputError : ErrorCode()
}