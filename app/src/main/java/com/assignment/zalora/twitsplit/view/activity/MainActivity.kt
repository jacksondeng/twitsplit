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
import com.assignment.zalora.twitsplit.adapter.TweetAdapter
import com.assignment.zalora.twitsplit.databinding.ActivityMainBinding
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.adapter.OnItemClickedCallback
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity(), OnDataPass,OnItemClickedCallback {

    private var msgUtils = MessageUtils()
    private lateinit var mainBinding : ActivityMainBinding
    private var logoutClicked = false
    private var tweetAdapter : TweetAdapter ?= null

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

    private fun loadTweets(){
        tweetVM.loadTweets()
    }

    private fun initObservers(){
        tweetVM.tweetList.observe(this, Observer {
            when(it){
                // Returns the list if it's not `null`, or an empty array otherwise.
                (it.orEmpty()) -> {
                    updatelist()
                }
            }
        })

        tweetVM.isUserSignedIn.observe(this, Observer {
            when(it){
                true ->{
                    loadTweets()
                } else -> {
                    if(logoutClicked) {
                        gotoAuth()
                    }
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
        btnLogout.setOnClickListener{
            logoutClicked = true
            tweetVM.signOut()
        }
    }

    private fun initAdapter(){
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(tweetVM))
        itemTouchHelper.attachToRecyclerView(tweet_list)
        if(tweetAdapter == null){
            tweetAdapter = TweetAdapter()
            tweetVM.tweetAdapter = tweetAdapter
        }
    }

    private fun updatelist(){
        tweetVM.setTweetList(tweetVM.tweetList.value!!)
        tweetVM.tweetAdapter?.notifyDataSetChanged()
        swipeContainer.isRefreshing = false
    }

    override fun gotoTweetDetails(tweetsDO: TweetsDO) {
        tweetVM.selectedTweet = tweetsDO
        val intent = Intent(this, TweetDetailsActivity::class.java)
        intent.putExtra("selectedTweet",tweetVM.selectedTweet)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        tweetVM.isUserSignedIn.removeObservers(this)
        tweetVM.tweetList.removeObservers(this)
        tweetVM.tweetAdapter?.context = null
    }

}

