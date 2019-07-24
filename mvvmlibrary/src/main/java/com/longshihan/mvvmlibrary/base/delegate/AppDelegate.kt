package com.longshihan.mvvmlibrary.base.delegate

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import com.longshihan.mvvmlibrary.base.App
import com.longshihan.mvvmlibrary.di.component.AppComponent
import com.longshihan.mvvmlibrary.di.component.AppComponentImpl
import com.longshihan.mvvmlibrary.di.module.AppModule
import com.longshihan.mvvmlibrary.di.module.ClientModule
import com.longshihan.mvvmlibrary.di.module.GlobalConfigModule
import com.longshihan.mvvmlibrary.intergration.ActivityLifecycle
import com.longshihan.mvvmlibrary.intergration.ConfigModule
import com.longshihan.mvvmlibrary.utils.ManifestParser
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class AppDelegate(application: Application) : App, AppLifecycles {
    private var mAppComponent: AppComponent? = null
    var mActivityLifecycle: ActivityLifecycle? = null
    var application: Application = application
    private var mModules: MutableList<ConfigModule> = ArrayList()
    private val mAppLifecycles: MutableList<AppLifecycles> = ArrayList()

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
        val appModule = AppModule(application)
        val clientModule = ClientModule()
        mAppComponent = AppComponentImpl(appModule, clientModule,
            getGlobalConfigModule(application, mModules), application
        )
        mActivityLifecycle = ActivityLifecycle()
        application.registerActivityLifecycleCallbacks(mActivityLifecycle)
        for (lifecycle in mAppLifecycles) {
            lifecycle.onCreate(application)
        }
        mModules= ArrayList()
    }

    override fun onTerminate(application: Application) {
        for (lifecycle in mAppLifecycles) {
            lifecycle.onTerminate(application)
        }
    }

    /**
     * 需要在AndroidManifest中声明[ConfigModule]的实现类,和Glide的配置方式相似
     *
     * @return
     */
    private fun getGlobalConfigModule(context: Context, modules: List<ConfigModule>): GlobalConfigModule {
        val builder = GlobalConfigModule.builder()
        //遍历 ConfigModule 集合, 给全局配置 GlobalConfigModule 添加参数
        for (module in modules) {
            module.applyOptions(context, builder)
        }
        return builder.build()
    }

}