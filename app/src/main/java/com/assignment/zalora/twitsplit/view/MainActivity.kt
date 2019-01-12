package com.assignment.zalora.twitsplit.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedQueryList
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.db.DynamoDbUtils
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.AWSProvider
import com.assignment.zalora.twitsplit.util.MessageUtils
import com.assignment.zalora.twitsplit.viewmodel.TweetVM
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val tweetVM = ViewModelProviders.of(this, viewModelFactory)[TweetVM::class.java]

        var msg ="I can't believe Tweeter now supports chunk"
        val msgUtils = MessageUtils()
        var msgList : List<String>

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            /*msgList = msgUtils.split(msg)
            for(index in msgList.indices){
                Timber.d("msg " + msgList.get(index))
                tweetVM.postTweet(msgList.get(index),index)
            }*/
            tweetVM.readTweets()
            tweetVM.tweetList.observe(this,Observer<PaginatedQueryList<TweetsDO>> {
                tweetVM.iterateTweets()
            })
        }

        if (!tweetVM.isUserSignedIn) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }


}
