package com.assignment.zalora.twitsplit.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.adapter.OnItemClickedCallback
import com.assignment.zalora.twitsplit.util.extension.listen
import kotlinx.android.synthetic.main.tweet_item.view.*
import timber.log.Timber
import javax.inject.Inject

class TweetAdapter @Inject constructor(): RecyclerView.Adapter<TweetAdapter.ViewHolder>(){

    var tweets : MutableList<TweetsDO> ?= null
    // Interface on to call method in parent activity
    var onItemClickedCallback : OnItemClickedCallback ?= null
    var context : Context ?= null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TweetAdapter.ViewHolder {
        context = parent.context
        onItemClickedCallback = context as OnItemClickedCallback

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.tweet_item, parent, false)
        return ViewHolder(view).listen { pos, type ->
            Timber.d("Tweets selected ${tweets!!.get(pos)}")
            onItemClickedCallback!!.gotoTweetDetails(tweets!!.get(pos))
        }
    }

    override fun getItemCount(): Int {
        if(tweets != null){
            return tweets!!.size
        }
        else{
            return 0
        }
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tweet = view.tweetTv
        val datePosted = view.datePostedTv
        val username = view.usernameTv
    }

    override fun onBindViewHolder(viewHolder: TweetAdapter.ViewHolder, pos: Int) {
        if(tweets != null) {
            viewHolder.tweet.text = tweets!!.get(pos).msg
            viewHolder.datePosted.text = tweets!!.get(pos).getPostedDateTime()
            viewHolder.username.text = tweets!!.get(pos).username
        }
    }

    fun setTweetList(tweets: MutableList<TweetsDO>){
        this.tweets = tweets
        this.notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        tweets?.removeAt(position)
        notifyItemRemoved(position)
    }

}