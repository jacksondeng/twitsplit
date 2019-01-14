package com.assignment.zalora.twitsplit.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.model.TweetsDO
import kotlinx.android.synthetic.main.tweet_item.view.*
import org.joda.time.DateTime
import javax.inject.Inject


class TweetAdapter @Inject constructor(): RecyclerView.Adapter<TweetAdapter.ViewHolder>(){

    var tweets : MutableList<TweetsDO> ?= null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TweetAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tweet_item, parent, false))
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
        val tweet = view.tweet_tv
        val datePosted = view.date_posted_tv
    }

    override fun onBindViewHolder(viewHolder: TweetAdapter.ViewHolder, pos: Int) {
        if(tweets != null) {
            viewHolder.tweet.text = tweets!!.get(pos).msg
            viewHolder.datePosted.text = getDateTimeFromLong(tweets!!.get(pos).creationDate.toLong())
        }
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
        tweets?.removeAt(position)
        notifyItemRemoved(position)
    }
}