package com.longshihan.mvvmlibrary.base

import android.os.Bundle
import android.support.v4.app.Fragment

abstract class BaseFragment<VM:BaseViewModel>: Fragment(),IBaseView {
    protected var viewModel: VM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun isBackPressed(): Boolean {
        return false
    }
}