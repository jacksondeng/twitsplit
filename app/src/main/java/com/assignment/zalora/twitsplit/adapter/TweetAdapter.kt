package com.assignment.zalora.twitsplit.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.viewmodel.TweetVM
import kotlinx.android.synthetic.main.tweet_item.view.*
import org.joda.time.DateTime


class TweetAdapter(var tweets : MutableList<TweetsDO>,val context: Context, val tweetVM: TweetVM): RecyclerView.Adapter<TweetAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TweetAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.tweet_item, parent, false))
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tweet = view.tweet_tv
        val datePosted = view.date_posted_tv
    }

    override fun onBindViewHolder(viewHolder: TweetAdapter.ViewHolder, pos: Int) {
        viewHolder.tweet?.text = tweets.get(pos).msg
        viewHolder.datePosted?.text = getDateTimeFromLong(tweets.get(pos).creationDate.toLong())
    }


    fun setTweetList(tweets: MutableList<TweetsDO>){
        this.tweets = tweets
        this.notifyDataSetChanged()
    }

    fun getDateTimeFromLong(millis : Long) :String{
        var dt = DateTime(millis)
        return dt.toLocalDateTime().toString()
    }

    fun removeAt(position: Int) {
        tweetVM.deleteTweets(tweets.get(position))
        tweets.removeAt(position)
        notifyItemRemoved(position)
    }
}