package com.assignment.zalora.twitsplit.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.FragmentManager
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.MessageUtils
import com.assignment.zalora.twitsplit.view.fragment.InputMsgDialogFragment
import com.assignment.zalora.twitsplit.view.dialog.OnDataPass

import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : BaseActivity(),OnDataPass {
    private var msgUtils = MessageUtils()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            showInputMsgDialog()
        }

        if (!tweetVM.isUserSignedIn) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }

    fun showInputMsgDialog(){
        var fm : FragmentManager = getSupportFragmentManager();
        var inputMsgDialog = InputMsgDialogFragment();
        inputMsgDialog.isCancelable = false
        inputMsgDialog.show(fm, "fragment_edit_name");
    }

    override fun onDataPass(msg: String) {
        Timber.d("Dialog dismiss " + msg + " " + msgUtils.split(msg).size)
        tweetVM.postTweet(msgUtils.split(msg))
    }


}

