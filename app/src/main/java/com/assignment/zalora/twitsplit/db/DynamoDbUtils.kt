package com.assignment.zalora.twitsplit.db

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.AWSProvider
import com.assignment.zalora.twitsplit.util.LoadingState
import timber.log.Timber
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class DynamoDbUtils(private var awsProvider: AWSProvider){
    private var dynamoDBMapper: DynamoDBMapper ? = null
    var loadingState : MutableLiveData<LoadingState> = MutableLiveData()

    fun createTweet(msg : String,index: Int) : TweetsDO {
        val tweet = TweetsDO()
        tweet.userId = awsProvider!!.identityManager?.cachedUserID
        tweet.msg = msg
        tweet.creationDate = getCurrentTimeStamp(index)
        return tweet
    }

    fun postTweet(msg : String,index : Int){
        if(awsProvider!!.identityManager.isUserSignedIn && dynamoDBMapper == null){
            initDbClient()
        }
        thread(start = true) {
            loadingState.postValue(LoadingState.Posting)
            dynamoDBMapper?.save(createTweet(msg,index))
        }.join()
        readTweet()
    }

    fun readTweet() : PaginatedQueryList<TweetsDO>?{
        loadingState.postValue(LoadingState.Loading)

        if(awsProvider!!.identityManager.isUserSignedIn && dynamoDBMapper == null){
            initDbClient()
        }
        var paginatedQueryList : PaginatedQueryList<TweetsDO> ?= null
        thread(start = true) {
            paginatedQueryList = dynamoDBMapper?.query(TweetsDO::class.java,createQueryExpression(10))
        }.join()

        if(paginatedQueryList==null){
            loadingState.postValue(LoadingState.Failed)
        } else{
            Handler().postDelayed({
                loadingState.postValue(LoadingState.Success)
            },500)
        }
        return paginatedQueryList
    }

    fun createQueryExpression(limit : Int) : DynamoDBQueryExpression<TweetsDO>{
        val queryExpression = DynamoDBQueryExpression<TweetsDO>()
        val tweet = TweetsDO()
        tweet.userId = awsProvider.identityManager.cachedUserID
        queryExpression
            .withHashKeyValues(tweet)
            .withLimit(limit)
        return queryExpression
    }

    fun initDbClient(){
        val client = AmazonDynamoDBClient(awsProvider!!.identityManager.credentialsProvider)
        dynamoDBMapper = DynamoDBMapper.builder()
            .dynamoDBClient(client)
            .awsConfiguration(AWSMobileClient.getInstance().configuration)
            .build()
    }

    fun getCurrentTimeStamp(prefix : Int) : String{
        return (System.currentTimeMillis()+prefix).toString()
    }
}