package com.assignment.zalora.twitsplit.util.adapter

import com.assignment.zalora.twitsplit.model.TweetsDO


interface OnItemClickedCallback {
    fun gotoTweetDetails(tweetsDO: TweetsDO)
}