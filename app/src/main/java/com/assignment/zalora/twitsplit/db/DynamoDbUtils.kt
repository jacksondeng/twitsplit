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
import com.assignment.zalora.twitsplit.util.SingleLiveEvent
import com.assignment.zalora.twitsplit.util.network.NetworkManager
import com.assignment.zalora.twitsplit.util.state.ErrorCode
import com.assignment.zalora.twitsplit.util.state.LoadingState
import timber.log.Timber
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class DynamoDbUtils(private var awsProvider: AWSProvider,private var networkManager: NetworkManager){

    var dynamoDBMapper: DynamoDBMapper ? = null
    var loadingState : SingleLiveEvent<LoadingState> =
        SingleLiveEvent()
    var tweetList : MutableLiveData<MutableList<TweetsDO>> = MutableLiveData()

    fun createTweet(msg : String,index: Int) : TweetsDO {
            val tweet = TweetsDO()
            tweet.userId = awsProvider.cachedUserID!!
            tweet.msg = msg
            tweet.creationDate = getCurrentTimeStamp(index)
            return tweet
    }

    fun postTweet(msgList : List<String>){
        if(isConnectedToNetwork() && checkCachedUserId()) {
            if (msgList.size == 0) {
                loadingState.postValue(LoadingState.Error(ErrorCode.InputError))
                return
            }

            dynamoDBMapper = initDbClient()

            loadingState.postValue(LoadingState.Posting)
            for (index in msgList.indices) {
                thread(start = true) {
                    dynamoDBMapper?.save(createTweet(msgList.get(index), index))
                }.join()

                loadingState.postValue(LoadingState.Loading)
            }
            // small delay to wait for dynamodb updates before loading tweets
            Handler().postDelayed({ loadTweets() }, 200)
        } else{
            loadingState.postValue(LoadingState.Error(ErrorCode.NetworkError))
        }
    }

    fun deleteTweet(tweetsDO: TweetsDO){
        if(isConnectedToNetwork() && checkCachedUserId()) {
            dynamoDBMapper = initDbClient()
            loadingState.postValue(LoadingState.Deleting)
            thread(start = true) {
                dynamoDBMapper?.delete(tweetsDO)
            }.join()
            loadingState.postValue(LoadingState.Loading)
            // small delay to wait for dynamodb updates before loading tweets
            Handler().postDelayed({ loadTweets() }, 200)
        } else{
            loadingState.postValue(LoadingState.Error(ErrorCode.NetworkError))
        }
    }

    fun loadTweets(){
        if(isConnectedToNetwork() && checkCachedUserId()) {
            dynamoDBMapper = initDbClient()
            var paginatedQueryList: PaginatedQueryList<TweetsDO>? = null
            thread(start = true) {
                paginatedQueryList = dynamoDBMapper?.query(TweetsDO::class.java, createQueryExpression(10))
            }.join()

            if (paginatedQueryList == null) {
                loadingState.postValue(LoadingState.Failed)
            } else {
                loadingState.postValue(LoadingState.Success)
            }
            tweetList.postValue(convertPaginatedListToList(paginatedQueryList))
        } else{
            loadingState.postValue(LoadingState.Error(ErrorCode.NetworkError))
        }
    }

    private fun createQueryExpression(limit : Int) : DynamoDBQueryExpression<TweetsDO>{
        val queryExpression = DynamoDBQueryExpression<TweetsDO>()
        if(isConnectedToNetwork() && checkCachedUserId()) {
            val tweet = TweetsDO()
            tweet.userId = awsProvider.cachedUserID!!
            queryExpression
                .withHashKeyValues(tweet)
                .withLimit(limit)
        }
        return queryExpression
    }


    fun initDbClient() : DynamoDBMapper{
        if(awsProvider.isUserSignedIn.value!! && dynamoDBMapper == null) {
            val client = AmazonDynamoDBClient(awsProvider.awsCredentials)
            return DynamoDBMapper.builder()
                .dynamoDBClient(client)
                .awsConfiguration(AWSMobileClient.getInstance().configuration)
                .build()
        }
        return dynamoDBMapper!!
    }

    private fun getCurrentTimeStamp(prefix : Int) : String{
        return (System.currentTimeMillis()+prefix).toString()
    }

    // Need to convert to MutableList for deleting tweet because paginatedQueryList cannot be modified
    private fun convertPaginatedListToList(paginatedQueryList: PaginatedQueryList<TweetsDO>?) : MutableList<TweetsDO>{
        var list = ArrayList<TweetsDO>()
        thread(start = true) {
            if (paginatedQueryList != null) {
                paginatedQueryList.forEach {
                    it.username = awsProvider.username
                    list.add(it)
                }
            }
        }.join()
        return list
    }

    private fun isConnectedToNetwork() : Boolean{
        return networkManager.isOnline()
    }

    private fun checkCachedUserId() : Boolean{
        return awsProvider.cachedUserID != null
    }

    // Clear dynamoDb instance for to reinitialize after user logged out
    fun clearDbInstance(){
        dynamoDBMapper = null
    }
}