package com.assignment.zalora.twitsplit.model


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable

@DynamoDBTable(tableName = "assignment-mobilehub-88096205-Tweets")
class TweetsDO {
    @get:DynamoDBHashKey(attributeName = "userId")
    @get:DynamoDBAttribute(attributeName = "userId")
    var userId: String? = null
        get() = field
        set(value) {
            field = value
        }

    @get:DynamoDBRangeKey(attributeName = "creationDate")
    @get:DynamoDBAttribute(attributeName = "creationDate")
    var creationDate: String? = null
        get() = field
        set(value) {
            field = value
        }

    @get:DynamoDBAttribute(attributeName = "msg")
    var msg: String? = null
        get() = field
        set(value) {
            field = value
        }
}
