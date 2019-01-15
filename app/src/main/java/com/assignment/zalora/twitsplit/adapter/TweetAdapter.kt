package com.assignment.zalora.twitsplit.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.adapter.OnItemClickedCallback
import com.assignment.zalora.twitsplit.util.adapter.listen
import kotlinx.android.synthetic.main.tweet_item.view.*
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

class TweetAdapter @Inject constructor(): RecyclerView.Adapter<TweetAdapter.ViewHolder>(){

    var tweets : MutableList<TweetsDO> ?= null
    var onItemClickedCallback : OnItemClickedCallback ?= null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TweetAdapter.ViewHolder {
        onItemClickedCallback = parent.context as OnItemClickedCallback

        val inflater = LayoutInflater.from(parent.getContext())
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
        val dt = DateTime(millis)
        return dt.toLocalDateTime().toString()
    }

    fun removeAt(position: Int) {
        tweets?.removeAt(position)
        notifyItemRemoved(position)
    }

}