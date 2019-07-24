package com.longshihan.mvvmlibrary.base.delegate

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import com.longshihan.mvvmlibrary.base.App
import com.longshihan.mvvmlibrary.di.component.AppComponent
import com.longshihan.mvvmlibrary.di.module.AppModule
import com.longshihan.mvvmlibrary.di.module.ClientModule
import com.longshihan.mvvmlibrary.intergration.ActivityLifecycle
import com.longshihan.mvvmlibrary.intergration.ConfigModule
import com.longshihan.mvvmlibrary.utils.ManifestParser
import java.util.*


/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class AppDelegate(application: Application) : App, AppLifecycles {
    private val mAppComponent: AppComponent? = null
    var mActivityLifecycle: ActivityLifecycle? = null
    var application: Application = application
    private var mModules: MutableList<ConfigModule> = ArrayList()
    private val mAppLifecycles: MutableList<AppLifecycles> = ArrayList()
    private val mActivityLifecycles: MutableList<Application.ActivityLifecycleCallbacks> = ArrayList()
    private val mComponentCallback: ComponentCallbacks2? = null

    init {
        mModules = ManifestParser(application).parse()
        for (module in mModules) {
            //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            mAppLifecycles.add(module.injectAppLifecycle(application))
        }
    }

    override fun getAppComponent(): AppComponent {
        return mAppComponent!!
    }

    override fun attachBaseContext(base: Context) {
        for (lifecycle in mAppLifecycles) {
            lifecycle.attachBaseContext(base)
        }

    }

    override fun onCreate(application: Application) {
        val appModule=AppModule(application)
        val clientModule=ClientModule()

        mActivityLifecycle = ActivityLifecycle()
        application.registerActivityLifecycleCallbacks(mActivityLifecycle)
        for (lifecycle in mAppLifecycles) {
            lifecycle.onCreate(application)
        }
    }

    override fun onTerminate(application: Application) {
        for (lifecycle in mAppLifecycles) {
            lifecycle.onTerminate(application)
        }
    }

}