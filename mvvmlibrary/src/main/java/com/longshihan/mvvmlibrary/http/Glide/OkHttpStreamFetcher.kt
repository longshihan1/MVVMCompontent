package com.longshihan.mvvmlibrary.http.Glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import com.longshihan.mvvmlibrary.utils.MLog
import okhttp3.*
import java.io.IOException
import java.io.InputStream


/**
 * Created by LONGHE001.
 * @time 2019/7/25 0025
 * @des
 * @function
 */
class OkHttpStreamFetcher(client: Call.Factory, url: GlideUrl) : DataFetcher<InputStream>, Callback {
    private val TAG = "OkHttpFetcher"
    private val client: Call.Factory? = null
    private val url: GlideUrl? = null
    private var stream: InputStream? = null
    private var responseBody: ResponseBody? = null
    private var callback: DataFetcher.DataCallback<in InputStream>? = null
    @Volatile
    private var call: Call? = null

    init {
        this.call = call
    }

    override fun getDataClass(): Class<InputStream> = InputStream::class.java

    override fun cleanup() {
        try {
            stream?.close()
            responseBody?.close()
        } catch (e: IOException) {
            // Ignored
        }
        callback = null
    }

    override fun getDataSource(): DataSource = DataSource.REMOTE

    override fun cancel() {
        val local = call
        local?.cancel()
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        val requestBuilder = Request.Builder().url(url!!.toStringUrl())
        for (headerEntry in url.headers.entries) {
            val key = headerEntry.key
            requestBuilder.addHeader(key, headerEntry.value)
        }
        val request = requestBuilder.build()
        this.callback = callback
        call = client!!.newCall(request)
        call!!.enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        MLog.d(TAG, "OkHttp failed to obtain result" + e.message)
        callback!!.onLoadFailed(e)
    }

    override fun onResponse(call: Call, response: Response) {
        responseBody = response.body()
        if (response.isSuccessful) {
            val contentLength = responseBody!!.contentLength()
            stream = ContentLengthInputStream.obtain(responseBody!!.byteStream(), contentLength)
            callback!!.onDataReady(stream)
        } else {
            callback!!.onLoadFailed(HttpException(response.message(), response.code()))
        }
    }

}