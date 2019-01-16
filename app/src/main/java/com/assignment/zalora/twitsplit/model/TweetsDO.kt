package com.assignment.zalora.twitsplit.model


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable
import org.joda.time.DateTime
import java.io.Serializable

@DynamoDBTable(tableName = "assignment-mobilehub-88096205-Tweets")
class TweetsDO() :Serializable{
    @get:DynamoDBHashKey(attributeName = "userId")
    @get:DynamoDBAttribute(attributeName = "userId")
    var userId: String = ""

    @get:DynamoDBRangeKey(attributeName = "creationDate")
    @get:DynamoDBAttribute(attributeName = "creationDate")
    var creationDate: String = ""

    @get:DynamoDBAttribute(attributeName = "msg")
    var msg: String? = ""
    var username: String? = ""

    fun getPostedDateTime() :String{
        val dt = DateTime(creationDate.toLong())
        return dt.toLocalDateTime().toString().replace("T"," ").replace("\\..*".toRegex(),"")
    }
}
