package com.longshihan.mvvmlibrary.intergration

import android.app.Application
import android.content.Context
import com.longshihan.mvvmlibrary.base.delegate.AppLifecycles


/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
interface ConfigModule {
    /**
     * 使用[GlobalConfigModule.Builder]给框架配置一些配置参数
     *
     * @param context
     * @param builder
     */
//    fun applyOptions(context: Context, builder: GlobalConfigModule.Builder)

    /**
     * 使用[AppLifecycles]在Application的生命周期中注入一些操作
     *
     * @return
     */
    fun injectAppLifecycle(application: Application):AppLifecycles

}