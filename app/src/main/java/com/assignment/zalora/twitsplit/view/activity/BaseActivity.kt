package com.assignment.zalora.twitsplit.view.activity

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.state.ErrorCode
import com.assignment.zalora.twitsplit.util.state.LoadingState
import com.assignment.zalora.twitsplit.util.state.StatusUtils
import com.assignment.zalora.twitsplit.view.fragment.ErrorDialogFragment
import com.assignment.zalora.twitsplit.view.fragment.InputMsgDialogFragment
import com.assignment.zalora.twitsplit.viewmodel.TweetVM
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import timber.log.Timber
import javax.inject.Inject
import com.assignment.zalora.twitsplit.util.aws.AWSProvider


open class BaseActivity : DaggerAppCompatActivity(), StatusUtils {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var tweetVM : TweetVM
    private lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        AndroidInjection.inject(this)
        initVm()
        initLoadingStateObservers()
        initLoadingDialog()
    }

    override fun showLoading() {
        if(!dialog.isShowing) {
            dialog.show()
        }
    }

    override fun showError(err: String,title: String) {
        showErrorDialog(err,title)
    }

    override fun hideLoading() {
        dialog.dismiss()
    }

    fun initVm(){
       tweetVM = ViewModelProviders.of(this, viewModelFactory)[TweetVM::class.java]
    }

    fun initLoadingStateObservers(){
        tweetVM.loadingState.observe(this, Observer {
            when(it){
                LoadingState.Loading,LoadingState.Deleting,LoadingState.Posting ->{
                    Timber.d("LoadingState Loading")
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

                LoadingState.Error(ErrorCode.InputError) ->{
                    Timber.d("LoadingState Input Error")
                    showError(getString(R.string.invalid_input_msg),getString(R.string.invalid_input))
                }

                LoadingState.Error(ErrorCode.NetworkError) ->{
                    Timber.d("LoadingState Network Error")
                    showError(getString(R.string.network_err_msg),getString(R.string.network_err))
                }

            }
        })
    }

    fun initLoadingDialog(){
        dialog = Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.progress_dialog)
     }

    fun showInputMsgDialog(){
        var fm : FragmentManager = getSupportFragmentManager()
        var inputMsgDialog = InputMsgDialogFragment()
        inputMsgDialog.isCancelable = false
        inputMsgDialog.show(fm, "fragment_dialog_input_msg")
    }

    fun showErrorDialog(errMsg : String,title:String){
        var fm : FragmentManager = getSupportFragmentManager();
        var errorDialog = ErrorDialogFragment.newInstance(errMsg,title)
        errorDialog.isCancelable = false
        errorDialog.show(fm, "fragment_dialog_error");
    }

    fun checkIfUserSignedIn() : Boolean{
        Timber.d("UserSignedIn ${tweetVM.isUserSignedIn.value} ${tweetVM.userName}")
        //return awsProvider.cachedUserID != null
        return tweetVM.isUserSignedIn.value!!
    }

    fun gotoAuth(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun gotoMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
