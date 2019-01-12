package com.assignment.zalora.twitsplit.db

import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.AWSProvider
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton
import kotlin.concurrent.thread


@Singleton
class DynamoDbUtils{
    private var dynamoDBMapper: DynamoDBMapper ? = null
    private var awsProvider : AWSProvider ?= null

    constructor(awsProvider: AWSProvider){
        this.awsProvider = awsProvider
    }

    fun createTweet(msg : String,index: Int) : TweetsDO {
        val tweet = TweetsDO(awsProvider?.identityManager?.cachedUserID,msg,getCurrentTimeStamp(index))
        Timber.d("Tweet timestamp " + tweet.creationDate)
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

    fun initDbClient(){
        val client = AmazonDynamoDBClient(awsProvider?.identityManager?.credentialsProvider)
        dynamoDBMapper = DynamoDBMapper.builder()
            .dynamoDBClient(client)
            .awsConfiguration(AWSMobileClient.getInstance().configuration)
            .build()
    }

    fun getCurrentTimeStamp(prefix : Int) : String{
        return (System.currentTimeMillis()+prefix).toString()
    }
}