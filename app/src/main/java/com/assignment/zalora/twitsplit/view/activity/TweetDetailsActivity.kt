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
    }

    private fun initBindings(){
        tweetDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tweet_details);
        tweetVM.selectedTweet = intent.extras.getSerializable("selectedTweet") as? TweetsDO
        tweetDetailsBinding.tweetVM = tweetVM
    }
}
