package com.assignment.zalora.twitsplit.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import com.assignment.zalora.twitsplit.R
import android.content.DialogInterface
import android.widget.Button
import com.assignment.zalora.twitsplit.view.dialog.OnDataPass

class InputMsgDialogFragment : DialogFragment() {
    // Interface to pass msg back to activity on fragment dismissal
    var dataPasser : OnDataPass ?= null
    var msgEt : EditText ?= null
    var btnTweet : Button?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_input_msg, container);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        msgEt = view.findViewById<EditText>(R.id.input_msg_et)
        msgEt?.requestFocus();
        btnTweet = view.findViewById<Button>(R.id.btn_tweet)
        initListeners()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        passData(msgEt?.text.toString())
    }


    // Pass msg back to activity on fragment dismissal
    fun passData(data: String) {
        dataPasser?.onDataPass(data)
    }

    fun initListeners(){
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        btnTweet?.setOnClickListener { activity?.onBackPressed()}
    }
}