package com.longshihan.mvvmlibrary.di.module

import android.app.Application
import android.content.Context
import android.support.annotation.Nullable
import com.google.gson.Gson
import com.longshihan.mvvmlibrary.http.GlobalHttpHandler
import com.longshihan.mvvmlibrary.http.GlobalHttpHeaderIntercepter
import com.longshihan.mvvmlibrary.http.HttpURL
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class ClientModule {
    val TIME_OUT = 30
    /**
     * @param builder
     * @param client
     * @param httpUrl
     * @return
     * @description:提供retrofit
     */
    fun provideRetrofit(
        application: Application, configuration: RetrofitConfiguration?,
        client: OkHttpClient, gson: Gson
    ): Retrofit {
        var builder = Retrofit.Builder()
        builder.baseUrl(HttpUrl.parse(HttpURL.baseUrl)!!)//域名
            .client(client)//设置okhttp

        configuration?.configRetrofit(application, builder)

        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
            .addConverterFactory(GsonConverterFactory.create(gson))//使用Gson
        return builder.build()
    }

    /**
     * 提供OkhttpClient
     * @param application 上下文
     * @param configuration
     * @param builder
     * @param interceptors 拦截器集合
     * @param handler
     * @return
     */
    fun provideClient(
        application: Application, configuration: OkhttpConfiguration?,
        interceptors: List<Interceptor>?
    ): OkHttpClient {
        var builder = OkHttpClient.Builder()
        builder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(GlobalHttpHeaderIntercepter())
        if (interceptors != null) {//如果外部提供了interceptor的集合则遍历添加
            for (interceptor in interceptors) {
                builder.addInterceptor(interceptor)
            }
        }
        configuration?.configOkhttp(application, builder)
        return builder.build()
    }


    interface RetrofitConfiguration {
        fun configRetrofit(context: Context, builder: Retrofit.Builder)
    }

    interface OkhttpConfiguration {
        fun configOkhttp(context: Context, builder: OkHttpClient.Builder)
    }
}