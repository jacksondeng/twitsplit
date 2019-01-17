package com.assignment.zalora.twitsplit.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.assignment.zalora.twitsplit.adapter.TweetAdapter
import com.assignment.zalora.twitsplit.db.DynamoDbUtils
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import com.assignment.zalora.twitsplit.util.SingleLiveEvent
import com.assignment.zalora.twitsplit.util.state.LoadingState
import javax.inject.Inject

class TweetVM @Inject constructor(private val dynamoDbUtils: DynamoDbUtils, val awsProvider: AWSProvider
                                  ,var tweetAdapter: TweetAdapter?)
    : ViewModel(), LifecycleObserver {

    var tweetList : MutableLiveData<MutableList<TweetsDO>> = dynamoDbUtils.tweetList
    var loadingState : SingleLiveEvent<LoadingState> = dynamoDbUtils.loadingState
    var isUserSignedIn : MutableLiveData<Boolean> = awsProvider.isUserSignedIn
    var userName : String? = awsProvider.username
    var selectedTweet : TweetsDO ?= null

    fun postTweet(msgList : List<String>){
        dynamoDbUtils.postTweet(msgList)
    }

    fun loadTweets() {
        dynamoDbUtils.loadTweets()
    }

    fun deleteTweets(tweetsDO: TweetsDO){
        dynamoDbUtils.deleteTweet(tweetsDO)
    }

    fun setTweetList(tweetList : MutableList<TweetsDO>){
        tweetAdapter?.setTweetList(tweetList)
    }

    fun signOut(){
        dynamoDbUtils.clearDbInstance()
        awsProvider.signOut()
    }

}