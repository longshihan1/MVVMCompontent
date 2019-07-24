package com.longshihan.mvvmlibrary.http

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class GlobalHttpHeaderIntercepter : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            var request = chain.request()
            val builder = request.newBuilder()
            val headerValues: List<String> = request.headers(HttpURL.HEADER_KEY)
            if (headerValues.isNotEmpty()) {
                //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                builder.removeHeader(HttpURL.HEADER_KEY)
                val headerValue = headerValues[0]
                if (headerValue.isNotEmpty()) {
                    var newBaseUrl = HttpURL.get(headerValue)
                    if (newBaseUrl.isNotEmpty()) {
                        val newBaseUrl = HttpUrl.parse(newBaseUrl)
                        if (newBaseUrl!=null){
                            val oldHttpUrl = request.url()
                            //重建新的HttpUrl，修改需要修改的url部分
                            val newFullUrl = oldHttpUrl
                                .newBuilder()
                                .scheme(newBaseUrl.scheme())
                                .host(newBaseUrl.host())
                                .port(newBaseUrl.port())
                                .build()
                            //重建这个request，通过builder.url(newFullUrl).build()；
                            //然后返回一个response至此结束修改
                            request = builder.url(newFullUrl).build()
                            return chain.proceed(request)
                        }
                    }
                }
            }
            return chain.proceed(chain.request())
        } catch (e: Exception) {
            return chain.proceed(chain.request())
        }
    }

}