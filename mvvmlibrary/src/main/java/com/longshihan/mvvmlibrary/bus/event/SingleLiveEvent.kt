package com.longshihan.mvvmlibrary.bus.event

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import com.longshihan.mvvmlibrary.utils.MLog
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
open class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val TAG = "SingleLiveEvent"

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {

        if (hasActiveObservers()) {
            MLog.d(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}