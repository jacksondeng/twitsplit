package com.assignment.zalora.twitsplit.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.databinding.ActivityTweetDetailsBinding
import com.assignment.zalora.twitsplit.model.TweetsDO
import timber.log.Timber

class TweetDetailsActivity : BaseActivity() {

    private lateinit var tweetDetailsBinding : ActivityTweetDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBindings()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initBindings(){
        tweetDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tweet_details);
        tweetVM.selectedTweet = getIntent().getExtras().getSerializable("selectedTweet") as? TweetsDO
        tweetDetailsBinding.tweetVM = tweetVM
    }

    private fun initListeners(){

    }
}
