package com.longshihan.mvvmlibrary.error

import android.content.Context

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
interface ResponseErrorListener {
    fun handleResponseError(context: Context, t: Throwable)
}

var EMPTY: ResponseErrorListener = object : ResponseErrorListener {
    override fun handleResponseError(context: Context, t: Throwable) {


    }
}