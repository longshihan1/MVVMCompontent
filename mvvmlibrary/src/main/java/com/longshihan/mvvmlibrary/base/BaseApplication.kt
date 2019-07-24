package com.longshihan.mvvmlibrary.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.longshihan.mvvmlibrary.base.delegate.AppDelegate
import com.longshihan.mvvmlibrary.base.delegate.AppLifecycles
import com.longshihan.mvvmlibrary.di.component.AppComponent

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class BaseApplication : Application(), App {

    var mAppLifecycles: AppLifecycles? = null
    var instance: BaseApplication? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        this.mAppLifecycles!!.onCreate(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        if (mAppLifecycles == null) mAppLifecycles = AppDelegate(this)
        mAppLifecycles!!.attachBaseContext(base)
    }

    override fun onTerminate() {
        super.onTerminate()
        this.mAppLifecycles!!.onTerminate(this)
    }

    override fun getAppComponent(): AppComponent {
        return (mAppLifecycles as App).getAppComponent()
    }

    companion object {
        lateinit var instance: BaseApplication
    }

}