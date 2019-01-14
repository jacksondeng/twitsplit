package com.assignment.zalora.twitsplit.view.activity

import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.adapter.TweetAdapter
import com.assignment.zalora.twitsplit.util.MessageUtils
import com.assignment.zalora.twitsplit.util.dialogFragment.OnDataPass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber
import android.support.v7.widget.DividerItemDecoration
import com.assignment.zalora.twitsplit.util.adapter.SwipeToDeleteCallback
import android.support.v7.widget.helper.ItemTouchHelper




class MainActivity : BaseActivity(), OnDataPass {

    private var msgUtils = MessageUtils()
    private var tweetAdapter : TweetAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initListeners()
        initTweetListObserver()
    }

    override fun onDataPass(msg: String) {
        tweetVM.postTweet(msgUtils.split(msg))
    }

    override fun onDataPass(refreshing: Boolean) {
        swipe_container?.isRefreshing = refreshing
    }


    override fun onResume() {
        super.onResume()
        loadTweets()
    }

    fun loadTweets(){
        tweetVM.loadTweets()
    }

    fun initTweetListObserver(){
        tweetVM.tweetList.observe(this, Observer {
            when(it){
                null -> {
                }
                else ->{
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
            tweetAdapter = TweetAdapter(tweetVM.tweetList.value!!, this,tweetVM)
        }else{
            tweetAdapter?.setTweetList(tweetVM.tweetList.value!!)
        }
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(tweetAdapter!!))
        itemTouchHelper.attachToRecyclerView(tweet_list)
        tweet_list.adapter = tweetAdapter
        tweet_list.setLayoutManager(LinearLayoutManager(this))
        tweet_list.addItemDecoration(DividerItemDecoration(this, HORIZONTAL))
        swipe_container.isRefreshing = false
    }


}

