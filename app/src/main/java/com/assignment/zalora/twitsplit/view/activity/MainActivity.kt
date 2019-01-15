package com.assignment.zalora.twitsplit.view.activity

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.MessageUtils
import com.assignment.zalora.twitsplit.util.dialogFragment.OnDataPass
import com.assignment.zalora.twitsplit.util.adapter.SwipeToDeleteCallback
import android.support.v7.widget.helper.ItemTouchHelper
import com.assignment.zalora.twitsplit.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnDataPass {

    private var msgUtils = MessageUtils()
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBindings()
        initListeners()
        initAdapter()
        initObservers()
    }

    override fun onDataPass(msg: String) {
        tweetVM.postTweet(msgUtils.split(msg))
    }

    override fun onDataPass(refreshing: Boolean) {
        swipeContainer.isRefreshing = refreshing
    }


    override fun onResume() {
        super.onResume()
        if(tweetVM.isUserSignedIn.value!!) {
            loadTweets()
        }
    }

    private fun loadTweets(){
        tweetVM.loadTweets()
    }

    private fun initObservers(){
        tweetVM.tweetList.observe(this, Observer {
            when(it){
                null -> {
                }
                else ->{
                    updatelist()
                }
            }
        })

        tweetVM.isUserSignedIn.observe(this, Observer {
            when(it){
                true ->{
                    loadTweets()
                } else -> {
                    gotoAuth()
                }
            }
        })

    }

    private fun initBindings(){
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.tweetVM = tweetVM
    }

    private fun initListeners(){
        postCl.setOnClickListener { showInputMsgDialog() }
        swipeContainer.setOnRefreshListener{ loadTweets() }
        btnLogout.setOnClickListener{ tweetVM.logout() }
    }

    private fun initAdapter(){
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(tweetVM))
        itemTouchHelper.attachToRecyclerView(tweet_list)
    }

    private fun updatelist(){
        tweetVM.setTweetList(tweetVM.tweetList.value!!)
        swipeContainer.isRefreshing = false
    }
}

