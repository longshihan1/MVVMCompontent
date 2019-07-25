package com.longshihan.mvvmlibrary.utils

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
object MLog {
    private var IS_SHOW_LOG = false
    fun init(isShowLog: Boolean) {
        IS_SHOW_LOG = isShowLog
    }
    fun d(message:String){
        d(MLog.javaClass.simpleName,message)
    }
    fun d(tag:String,message:String){

    }
}