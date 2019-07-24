package com.longshihan.mvvmlibrary.di.component

import android.app.Application
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.util.concurrent.ExecutorService
import com.longshihan.mvvmlibrary.http.IRepositoryManager




/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
interface AppComponent {
    fun application(): Application

    //OKHttp
    fun okHttpClient(): OkHttpClient

    //gson
    fun gson(): Gson
    /**
     * 用于管理网络请求层, 以及数据缓存层
     *
     * @return [IRepositoryManager]
     */
    fun repositoryManager(): IRepositoryManager

    /**
     * 返回一个全局公用的线程池,适用于大多数异步需求。
     * 避免多个线程池创建带来的资源消耗。
     *
     * @return [ExecutorService]
     */
    fun executorService(): ExecutorService

}