package com.assignment.zalora.twitsplit.view.fragment

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.assignment.zalora.twitsplit.R
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder

class Tutorial2 : Fragment(),ISlideBackgroundColorHolder {

    private var layoutContainer : FrameLayout?= null
    override fun getDefaultBackgroundColor(): Int {
        return Color.parseColor("#ff33b5e5");
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    fun initViews(view : View){
        layoutContainer = view.findViewById(R.id.layoutContainer)
    }


    override fun setBackgroundColor(backgroundColor: Int) {
        // Set the background color of the view within your slide to which the transition should be applied.
        if (layoutContainer != null) {
            layoutContainer!!.setBackgroundColor(backgroundColor);
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial2, container, false)
    }
}
