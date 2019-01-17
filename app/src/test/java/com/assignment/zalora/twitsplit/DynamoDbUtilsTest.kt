package com.assignment.zalora.twitsplit

import android.content.Context
import com.assignment.zalora.twitsplit.db.DynamoDbUtils
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import com.assignment.zalora.twitsplit.util.network.NetworkManager
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.util.Log
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.state.ErrorCode
import com.assignment.zalora.twitsplit.util.state.LoadingState
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate

@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(MockitoJUnitRunner::class) //this line allows you to use the powermock runner and mockito runner
@PrepareForTest(Log::class)

class DynamoDbUtilsTest{
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private var mockContext : Context = Mockito.mock(Context::class.java)
    private var awsProvider = AWSProvider(mockContext)
    private var networkManager = NetworkManager(mockContext)
    private var dynamoDbUtils = DynamoDbUtils(awsProvider,networkManager)
    inline fun <reified T> testMock(): T = mock(T::class.java)

    @Before
    fun init() {
        awsProvider.cachedUserID = "123456"
        awsProvider.isUserSignedIn.postValue(true)
        PowerMockito.mockStatic(Log::class.java)
        dynamoDbUtils.initDbClient()
    }

    @Test
    fun createTweetTest() {
        var msg = "test"
        val tweet = TweetsDO()
        tweet.msg = msg
        tweet.userId = awsProvider.cachedUserID!!
        //CreationDate will not be the same since its get from System current millis
        Assert.assertEquals(tweet.msg,dynamoDbUtils.createTweet(msg,0).msg)
        Assert.assertEquals(tweet.userId,dynamoDbUtils.createTweet(msg,0).userId)
    }

    @Test
    fun postTweetsTest(){
        var msgList = arrayListOf<String>("ABC")
        dynamoDbUtils.postTweet(msgList)
        val observer = testMock<(LoadingState) -> Unit>()
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        dynamoDbUtils.loadingState.observe({lifecycle}){
            it?.let(observer)
        }
        verify(observer).invoke(LoadingState.Error(ErrorCode.NetworkError))
    }

}