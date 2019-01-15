package com.assignment.zalora.twitsplit.util.extension

import android.databinding.adapters.NumberPickerBindingAdapter.setValue
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import android.support.annotation.Nullable
import java.util.concurrent.atomic.AtomicBoolean


class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {

        if (hasActiveObservers()) {
        }

        // Observe the internal MutableLiveData
        super.observe(owner, object : Observer<T> {
            override fun onChanged(t: T?) {
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(t)
                }
            }

        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        setValue(null)
    }

    companion object {

        private val TAG = "SingleLiveEvent"
    }
}