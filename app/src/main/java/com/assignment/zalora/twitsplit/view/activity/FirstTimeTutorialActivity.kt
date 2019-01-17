package com.assignment.zalora.twitsplit.view.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import com.assignment.zalora.twitsplit.view.fragment.Tutorial1
import com.assignment.zalora.twitsplit.view.fragment.Tutorial2
import com.assignment.zalora.twitsplit.view.fragment.Tutorial3
import com.assignment.zalora.twitsplit.view.fragment.Tutorial4
import com.github.paolorotolo.appintro.AppIntro
import dagger.android.AndroidInjection
import javax.inject.Inject


class FirstTimeTutorialActivity : AppIntro() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        addSlides()
        // Hide Skip/Done button.
        showSkipButton(true);
        isProgressButtonEnabled = true;
        setSlideOverAnimation()
    }

    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        updateSharedPreference()
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        updateSharedPreference()
    }

    fun updateSharedPreference(){
        sharedPreferences.edit().putBoolean("shouldShowTutorial",false).apply()
        finish()
    }

    fun addSlides(){
        addSlide(Tutorial1())
        addSlide(Tutorial2())
        addSlide(Tutorial3())
        addSlide(Tutorial4())
    }
}