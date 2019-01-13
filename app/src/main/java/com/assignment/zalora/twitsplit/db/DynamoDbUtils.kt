package com.assignment.zalora.twitsplit.db

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import com.assignment.zalora.twitsplit.util.state.LoadingState
import timber.log.Timber
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class DynamoDbUtils(private var awsProvider: AWSProvider){
    private var dynamoDBMapper: DynamoDBMapper ? = null
    var loadingState : MutableLiveData<LoadingState> = MutableLiveData()
    var tweetList : MutableLiveData<PaginatedQueryList<TweetsDO>> = MutableLiveData()
    var simpleList : MutableLiveData<MutableList<TweetsDO>> = MutableLiveData()

    fun createTweet(msg : String,index: Int) : TweetsDO {
        val tweet = TweetsDO()
        tweet.userId = awsProvider!!.identityManager?.cachedUserID
        tweet.msg = msg
        tweet.creationDate = getCurrentTimeStamp(index)
        return tweet
    }

    fun postTweet(msgList : List<String>){
        if(msgList.size == 0){
            loadingState.postValue(LoadingState.Error)
            return
        }

        initDbClient()

        loadingState.postValue(LoadingState.Posting)
        for(index in msgList.indices) {
            thread(start = true) {
                dynamoDBMapper?.save(createTweet(msgList.get(index), index))
            }.join()

            loadingState.postValue(LoadingState.Loading)
        }
        // Wait 200ms before loading tweets
        Handler().postDelayed({ loadTweets() },200)
    }

    fun deleteTweet(tweetsDO: TweetsDO){
        initDbClient()
        loadingState.postValue(LoadingState.Posting)
        thread(start = true) {
            dynamoDBMapper?.delete(tweetsDO)
        }.join()
        loadingState.postValue(LoadingState.Loading)
        // Wait 200ms before loading tweets
        Handler().postDelayed({ loadTweets() },200)
    }

    fun loadTweets(){
        initDbClient()
        var paginatedQueryList : PaginatedQueryList<TweetsDO> ?= null
        thread(start = true) {
            paginatedQueryList = dynamoDBMapper?.query(TweetsDO::class.java,createQueryExpression(30))
        }.join()

        if(paginatedQueryList==null){
            loadingState.postValue(LoadingState.Failed)
        } else{
            loadingState.postValue(LoadingState.Success)
        }
        simpleList.postValue(convertPaginatedListToList(paginatedQueryList))
        tweetList.postValue(paginatedQueryList)
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
        if(awsProvider.identityManager.isUserSignedIn && dynamoDBMapper == null) {

            val client = AmazonDynamoDBClient(awsProvider!!.identityManager.credentialsProvider)
            dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(client)
                .awsConfiguration(AWSMobileClient.getInstance().configuration)
                .build()
        }
    }

    fun getCurrentTimeStamp(prefix : Int) : String{
        return (System.currentTimeMillis()+prefix).toString()
    }

    fun convertPaginatedListToList(paginatedQueryList: PaginatedQueryList<TweetsDO>?) : MutableList<TweetsDO>{
        var list = ArrayList<TweetsDO>()
        if(paginatedQueryList!=null) {
            paginatedQueryList.forEach {
                list.add(it)
            }
        }
        return list
    }
}