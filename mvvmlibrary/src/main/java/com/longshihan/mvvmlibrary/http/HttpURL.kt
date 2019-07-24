package com.longshihan.mvvmlibrary.http

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
object HttpURL {
    var HEADER_KEY = "url_name"
    var baseUrl: String = "https://WWW.baidu.com"
    var urlMap = hashMapOf<String, String>()

    fun put(key: String, value: String) {
        urlMap.put(key, value)
    }

    fun get(key: String): String = urlMap[key]!!
}