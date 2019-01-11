package com.assignment.zalora.twitsplit

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.amazonaws.mobile.auth.ui.SignInUI
import com.assignment.zalora.twitsplit.util.AWSProvider

import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Pull in the SignInUI
        val ui = AWSProvider.instance!!.getClient(this, SignInUI::class.java) as SignInUI
        ui.login(this, MainActivity::class.java).execute()
    }

}
