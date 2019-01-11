package com.assignment.zalora.twitsplit.model


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable

@DynamoDBTable(tableName = "assignment-mobilehub-88096205-Tweets")
class TweetsDO(userId : String?, msg : String, creationDate : String) {
    @get:DynamoDBHashKey(attributeName = "userId")
    @get:DynamoDBAttribute(attributeName = "userId")
    var userId: String? = userId

    @get:DynamoDBRangeKey(attributeName = "creationDate")
    @get:DynamoDBAttribute(attributeName = "creationDate")
    var creationDate: String? = creationDate

    @get:DynamoDBAttribute(attributeName = "msg")
    var msg: String? = msg
}
