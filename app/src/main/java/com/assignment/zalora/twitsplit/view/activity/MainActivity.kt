package com.assignment.zalora.twitsplit.view.activity

import android.arch.lifecycle.Observer
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
        initTweetListObserver()
    }

    override fun onDataPass(msg: String) {
        tweetVM.postTweet(msgUtils.split(msg))
    }

    override fun onDataPass(refreshing: Boolean) {
        swipe_container.isRefreshing = refreshing
    }


    override fun onResume() {
        super.onResume()
        loadTweets()
    }

    private fun loadTweets(){
        tweetVM.loadTweets()
    }

    private fun initTweetListObserver(){
        tweetVM.tweetList.observe(this, Observer {
            when(it){
                null -> {
                }
                else ->{
                    updatelist()
                }
            }
        })
    }

    private fun initBindings(){
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.tweetVM = tweetVM
    }

    private fun initListeners(){
        post_cl.setOnClickListener { showInputMsgDialog() }
        swipe_container.setOnRefreshListener{ loadTweets() }
    }

    private fun initAdapter(){
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(tweetVM))
        itemTouchHelper.attachToRecyclerView(tweet_list)
    }

    private fun updatelist(){
        tweetVM.setTweetList(tweetVM.tweetList.value!!)
        swipe_container.isRefreshing = false
    }
}

