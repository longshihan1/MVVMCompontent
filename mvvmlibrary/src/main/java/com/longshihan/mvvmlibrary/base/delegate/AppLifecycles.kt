package com.longshihan.mvvmlibrary.base.delegate

import android.app.Application
import android.content.Context

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
interface AppLifecycles {

    fun attachBaseContext(base: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}
