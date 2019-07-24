package com.longshihan.mvvmlibrary.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder


/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class AppModule(application1: Application) {
    val application: Application = application1
    fun provideGson(application: Application, configuration: GsonConfiguration?): Gson {
        val builder = GsonBuilder()
        configuration?.configGson(application, builder)
        return builder.create()
    }

    interface GsonConfiguration {
        fun configGson(context: Context, builder: GsonBuilder)
    }
}