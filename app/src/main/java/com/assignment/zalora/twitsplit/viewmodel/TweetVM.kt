package com.assignment.zalora.twitsplit.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.assignment.zalora.twitsplit.adapter.TweetAdapter
import com.assignment.zalora.twitsplit.db.DynamoDbUtils
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import com.assignment.zalora.twitsplit.util.network.NetworkManager
import com.assignment.zalora.twitsplit.util.state.LoadingState
import javax.inject.Inject

class TweetVM @Inject constructor(private val dynamoDbUtils: DynamoDbUtils, private val awsProvider: AWSProvider
                                  ,val tweetAdapter: TweetAdapter)
    : ViewModel(), LifecycleObserver {

    var tweetList : MutableLiveData<MutableList<TweetsDO>> = dynamoDbUtils.tweetList
    var loadingState : MutableLiveData<LoadingState> = dynamoDbUtils.loadingState

    fun postTweet(msgList : List<String>){
        dynamoDbUtils.postTweet(msgList)
    }

    fun loadTweets() {
        dynamoDbUtils.loadTweets()
    }

    fun deleteTweets(tweetsDO: TweetsDO){
        dynamoDbUtils.deleteTweet(tweetsDO)
    }

    fun getTweetAt(position : Int) : TweetsDO?{
        return tweetList.value?.get(position)
    }

    fun setTweetList(tweetList : MutableList<TweetsDO>){
        tweetAdapter.setTweetList(tweetList)
    }

}