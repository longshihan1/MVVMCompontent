package com.longshihan.mvvmlibrary.di.module

import com.longshihan.mvvmlibrary.error.EMPTY
import com.longshihan.mvvmlibrary.error.ResponseErrorListener
import okhttp3.Interceptor
import okhttp3.internal.Util.threadFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class GlobalConfigModule(builder: Builder) {
    private var mInterceptors: List<Interceptor>? = null
    private var mErrorListener: ResponseErrorListener? = null
    private var mRetrofitConfiguration: ClientModule.RetrofitConfiguration? = null
    private var mOkhttpConfiguration: ClientModule.OkhttpConfiguration? = null
    private var mGsonConfiguration: AppModule.GsonConfiguration? = null
    private var mExecutorService: ExecutorService? = null

    /**
     * @author: jess
     * @date 8/5/16 11:03 AM
     * @description: 设置baseurl
     */
    init {
        this.mInterceptors = builder.interceptors
        this.mRetrofitConfiguration = builder.retrofitConfiguration
        this.mOkhttpConfiguration = builder.okhttpConfiguration
        this.mGsonConfiguration = builder.gsonConfiguration
        this.mErrorListener = builder.responseErrorListener
        this.mExecutorService = builder.executorService
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }


    fun provideInterceptors(): List<Interceptor>? {
        return mInterceptors
    }


    /**
     * 提供处理Rxjava错误的管理器的回调
     *
     * @return
     */
    fun provideResponseErrorListener(): ResponseErrorListener {
        return mErrorListener ?: EMPTY
    }


    fun provideRetrofitConfiguration(): ClientModule.RetrofitConfiguration? {
        return mRetrofitConfiguration
    }


    fun provideOkhttpConfiguration(): ClientModule.OkhttpConfiguration? {
        return mOkhttpConfiguration
    }


    fun provideGsonConfiguration(): AppModule.GsonConfiguration? {
        return mGsonConfiguration
    }


    /**
     * 返回一个全局公用的线程池,适用于大多数异步需求。
     * 避免多个线程池创建带来的资源消耗。
     *
     * @return
     */
    fun provideExecutorService(): ExecutorService {
        return mExecutorService ?: ThreadPoolExecutor(
            0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
            SynchronousQueue<Runnable>(), threadFactory("Arms Executor", false)
        )
    }

    class Builder public constructor() {
        public var interceptors: MutableList<Interceptor>? = null
        public var responseErrorListener: ResponseErrorListener? = null
        public var retrofitConfiguration: ClientModule.RetrofitConfiguration? = null
        public var okhttpConfiguration: ClientModule.OkhttpConfiguration? = null
        public var gsonConfiguration: AppModule.GsonConfiguration? = null
        public var executorService: ExecutorService? = null

        fun addInterceptor(interceptor: Interceptor): Builder {//动态添加任意个interceptor
            if (interceptors == null)
                interceptors = ArrayList()
            this.interceptors!!.add(interceptor)
            return this
        }

        fun responseErrorListener(listener: ResponseErrorListener): Builder {//处理所有Rxjava的onError逻辑
            this.responseErrorListener = listener
            return this
        }


        fun retrofitConfiguration(retrofitConfiguration: ClientModule.RetrofitConfiguration): Builder {
            this.retrofitConfiguration = retrofitConfiguration
            return this
        }

        fun okhttpConfiguration(okhttpConfiguration: ClientModule.OkhttpConfiguration): Builder {
            this.okhttpConfiguration = okhttpConfiguration
            return this
        }


        fun gsonConfiguration(gsonConfiguration: AppModule.GsonConfiguration): Builder {
            this.gsonConfiguration = gsonConfiguration
            return this
        }


        fun executorService(executorService: ExecutorService): Builder {
            this.executorService = executorService
            return this
        }


        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }
}
