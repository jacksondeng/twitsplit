package com.assignment.zalora.twitsplit.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList
import com.assignment.zalora.twitsplit.db.DynamoDbUtils
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.AWSProvider
import com.assignment.zalora.twitsplit.util.LoadingState
import timber.log.Timber
import javax.inject.Inject

class TweetVM @Inject constructor(private val dynamoDbUtils: DynamoDbUtils, private val awsProvider: AWSProvider)
    : ViewModel(), LifecycleObserver {

    var tweetList : MutableLiveData<PaginatedQueryList<TweetsDO>> = MutableLiveData()
    var isUserSignedIn : Boolean
    var loadingState : MutableLiveData<LoadingState> = dynamoDbUtils.loadingState
    init {
        this.isUserSignedIn = awsProvider.identityManager.isUserSignedIn()
    }

    fun postTweet(msgList : List<String>){
        dynamoDbUtils.postTweet(msgList)
    }

    fun readTweets(){
        tweetList.postValue(dynamoDbUtils.readTweet())
    }

    fun iterateTweets(){
        tweetList.value!!.forEach {
            Timber.d("Tweets " + it.msg)
        }
    }

}