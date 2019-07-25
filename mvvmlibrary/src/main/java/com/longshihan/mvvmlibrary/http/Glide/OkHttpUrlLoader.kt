package com.longshihan.mvvmlibrary.http.Glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.longshihan.mvvmlibrary.http.Glide.OkHttpStreamFetcher
import okhttp3.Call
import java.io.InputStream

/**
 * Created by LONGHE001.
 * @time 2019/7/25 0025
 * @des
 * @function
 */
class OkHttpUrlLoader(client1: Call.Factory) : ModelLoader<GlideUrl, InputStream> {
    var client: Call.Factory? = null

    init {
        client = client1
    }

    override fun buildLoadData(
        model: GlideUrl,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(model, OkHttpStreamFetcher(client!!, model))
    }

    override fun handles(model: GlideUrl): Boolean = true

}