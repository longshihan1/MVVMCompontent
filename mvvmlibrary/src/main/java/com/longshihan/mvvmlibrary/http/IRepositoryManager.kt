package com.longshihan.mvvmlibrary.http

import android.content.Context

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des 用于管理所有仓库,网络请求层,以及数据缓存层
 * @function
 */
interface IRepositoryManager {

    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
    </T> */
    fun <T> obtainRetrofitService(service: Class<T>): T

    /**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache
     * @param <T>
     * @return
    </T> */
//    fun <T> obtainCacheService(cache: Class<T>): T

    /**
     * 清理所有缓存
     */
    fun clearAllCache()
}