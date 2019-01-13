package com.assignment.zalora.twitsplit.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.assignment.zalora.twitsplit.R
import timber.log.Timber

class ErrorDialogFragment : DialogFragment() {
    var errorMsgTv : TextView ?= null
    var btnDismiss : ImageButton ?= null
    var errorMsg : String ?= null
    var title : String ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_error, container);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("title")?.let {
            title = it
        }
        arguments?.getString("errMsg")?.let {
            errorMsg = it
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title).setNeutralButton(activity!!.getString(R.string.dismiss), null)
        builder.setMessage(errorMsg)
        return builder.create()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
    }

    fun initViews(view : View){
        errorMsgTv = view.findViewById(R.id.err_msg_tv)
        Timber.d("Error MSG $errorMsg")
        errorMsgTv?.setText(errorMsg)
        btnDismiss = view.findViewById(R.id.btn_dismiss)
    }

    fun initListeners(){
        btnDismiss?.setOnClickListener{ this.dismiss() }
    }

    companion object {
        fun newInstance(errorMsg: String,title: String): ErrorDialogFragment {
            val frag = ErrorDialogFragment()
            val args = Bundle()
            args.putString("errMsg",errorMsg)
            args.putString("title",title)
            frag.setArguments(args)
            return frag
        }
    }
}