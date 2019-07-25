package com.longshihan.mvvmlibrary.http.Glide

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import java.io.InputStream

/**
 * Created by LONGHE001.
 * @time 2019/7/25 0025
 * @des
 * @function
 */
class GlideModelLoaderFactory(client: Call.Factory) : ModelLoaderFactory<GlideUrl, InputStream> {
    var call: Call.Factory? = null

    init {
        call = client
    }

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
        return OkHttpUrlLoader(call!!)
    }

    override fun teardown() {
    }

}