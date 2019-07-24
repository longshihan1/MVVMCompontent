package com.longshihan.mvvmlibrary.http

import android.app.Application
import android.content.Context
import retrofit2.Retrofit


/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class RepositoryManager(retrofit: Retrofit) : IRepositoryManager {
    private val mRetrofit: Retrofit = retrofit

    override fun <T> obtainRetrofitService(service: Class<T>): T {
        return mRetrofit.create(service)
    }

//    override fun <T> obtainCacheService(cache: Class<T>): T {
//    }

    override fun clearAllCache() {
    }

}