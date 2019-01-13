package com.assignment.zalora.twitsplit.view.activity

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.adapter.TweetAdapter
import com.assignment.zalora.twitsplit.util.MessageUtils
import com.assignment.zalora.twitsplit.util.dialogFragment.OnDataPass

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber
import android.support.v4.widget.SwipeRefreshLayout




class MainActivity : BaseActivity(), OnDataPass {
    private var msgUtils = MessageUtils()
    private var tweetAdapter : TweetAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if(checkIfUserSignedIn()){
            loadTweets()
        }else{
            gotoAuth()
        }
        initListeners()
        initTweetListObserver()
    }

    override fun onDataPass(msg: String) {
        tweetVM.postTweet(msgUtils.split(msg))
    }

    override fun onResume() {
        super.onResume()
        if(checkIfUserSignedIn()) {
            loadTweets()
        }
    }

    fun checkIfUserSignedIn() : Boolean{
        return tweetVM.isUserSignedIn
    }

    fun gotoAuth(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }

    fun loadTweets(){
        tweetVM.loadTweets()
    }

    fun initTweetListObserver(){
        tweetVM.tweetList.observe(this, Observer {
            when(it){
                null -> {
                    Timber.d("TweetList null")
                }
                else ->{
                    Timber.d("TweetList " + it.size)
                    initAdapter()
                }
            }
        })
    }

    fun initListeners(){
        fab.setOnClickListener { showInputMsgDialog() }
        swipe_container.setOnRefreshListener{ loadTweets() }
    }

    fun initAdapter(){
        if(tweetAdapter == null) {
            tweetAdapter = TweetAdapter(tweetVM.tweetList.value!!, this)
        }else{
            tweetAdapter?.setTweetList(tweetVM.tweetList.value!!)
        }
        tweet_list.adapter = tweetAdapter
        tweet_list.setLayoutManager(LinearLayoutManager(this))
        swipe_container.isRefreshing = false
    }

}

