package com.assignment.zalora.twitsplit.db

import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.AWSProvider
import timber.log.Timber
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class DynamoDbUtils(private var awsProvider: AWSProvider){
    private var dynamoDBMapper: DynamoDBMapper ? = null

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
            dynamoDBMapper?.save(createTweet(msg,index))
        }
    }

    fun readTweet() : PaginatedQueryList<TweetsDO>?{
        if(awsProvider!!.identityManager.isUserSignedIn && dynamoDBMapper == null){
            initDbClient()
        }
        var paginatedQueryList : PaginatedQueryList<TweetsDO> ?= null
        thread(start = true) {
            val queryExpression = DynamoDBQueryExpression<TweetsDO>()
            val tweet = TweetsDO()
            tweet.userId = awsProvider!!.identityManager.cachedUserID
            queryExpression
                .withHashKeyValues(tweet)
                .withLimit(10)
            paginatedQueryList = dynamoDBMapper?.query(TweetsDO::class.java,queryExpression)
        }.join()
        return paginatedQueryList
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