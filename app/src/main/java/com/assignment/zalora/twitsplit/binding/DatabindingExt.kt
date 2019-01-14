package com.assignment.zalora.twitsplit.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.ClipDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


@BindingAdapter("setAdapter")
fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, ClipDrawable.HORIZONTAL))
    recyclerView.adapter = adapter
}