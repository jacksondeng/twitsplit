package com.assignment.zalora.twitsplit.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList
import com.assignment.zalora.twitsplit.db.DynamoDbUtils
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.AWSProvider
import com.assignment.zalora.twitsplit.util.MessageUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class TweetVM @Inject constructor(private val dynamoDbUtils: DynamoDbUtils, private val awsProvider: AWSProvider)
    : ViewModel(), LifecycleObserver {

    private var tweetList : MutableLiveData<PaginatedQueryList<TweetsDO>> = MutableLiveData()
    var isUserSignedIn : Boolean
    init {
        this.isUserSignedIn = awsProvider.identityManager.isUserSignedIn()
    }

    fun postTweet(msg : String ,index : Int){
        dynamoDbUtils.postTweet(msg,index)
    }

    fun readTweets(){
        tweetList.postValue(dynamoDbUtils.readTweet())
        if(tweetList.value!=null) {
            iterateTweets()
        }
    }

    fun iterateTweets(){
        tweetList.value!!.forEach {
            Timber.d("Tweets " + it.msg)
        }
    }

}