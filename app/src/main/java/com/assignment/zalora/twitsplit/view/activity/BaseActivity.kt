package com.assignment.zalora.twitsplit.view.activity

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.LoadingState
import com.assignment.zalora.twitsplit.util.StatusUtils
import com.assignment.zalora.twitsplit.viewmodel.TweetVM
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import timber.log.Timber
import javax.inject.Inject


open class BaseActivity : DaggerAppCompatActivity(), StatusUtils {
    private lateinit var dialog : Dialog

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var tweetVM : TweetVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        AndroidInjection.inject(this)
        initVm()
        initObservers()
        initDialog()
    }

    override fun showLoading() {
        if(!dialog.isShowing) {
            dialog.show()
        }
    }

    override fun showError(err: String) {

    }

    override fun hideLoading() {
        dialog.dismiss()
    }

    fun initVm(){
       tweetVM = ViewModelProviders.of(this, viewModelFactory)[TweetVM::class.java]
    }

    fun initObservers(){
        tweetVM.loadingState.observe(this, Observer {
            when(it){
                LoadingState.Loading ->{
                    Timber.d("LoadingState Loading")
                    showLoading()
                }

                LoadingState.Posting ->{
                    Timber.d("LoadingState Posting")
                    showLoading()
                }

                LoadingState.Success ->{
                    Timber.d("LoadingState Success")
                    hideLoading()
                }

                LoadingState.Failed ->{
                    Timber.d("LoadingState Failed")
                    hideLoading()
                }

                LoadingState.Error ->{
                    Timber.d("LoadingState Error")
                    showError("Input error")
                }
            }
        })
    }

    fun initDialog(){
        dialog = Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.progress_dialog)
    }

}
