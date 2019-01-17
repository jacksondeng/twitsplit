package com.assignment.zalora.twitsplit.view.fragment

import android.app.Dialog
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
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.ImageButton
import com.assignment.zalora.twitsplit.util.dialogFragment.OnDataPass

class InputMsgDialogFragment : DialogFragment() {
    // Interface to pass msg back to activity on fragment dismissal
    var dataPasser : OnDataPass?= null
    var msgEt : EditText ?= null
    var btnTweet : Button?= null
    var btnCancel : ImageButton ?= null
    var canceledByUser : Boolean = false

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
        initViews(view)
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        initDialog(dialog)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
    }

    // Pass msg back to activity on fragment dismissal
    fun passData(data: String) {
        dataPasser?.onDataPass(data)
    }

    fun initViews(view: View){
        msgEt = view.findViewById(R.id.input_msg_et)
        msgEt?.requestFocus();
        btnTweet = view.findViewById(R.id.btn_tweet)
        btnCancel = view.findViewById(R.id.btn_cancel)
    }

    fun initDialog(dialog : Dialog){
        val width = WindowManager.LayoutParams.MATCH_PARENT
        val height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window?.setLayout(width, height)
        dialog.window?.attributes?.windowAnimations = R.style.DialogSlide
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }


    fun initListeners(){
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        btnTweet?.setOnClickListener {
            this.dismiss()
            passData(msgEt?.text.toString())
        }
        btnCancel?.setOnClickListener{ this.dismiss() }
    }
}