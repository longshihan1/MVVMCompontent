package com.longshihan.mvvmlibrary.utils

import java.io.Closeable
import java.io.IOException

/**
 * Created by LONGHE001.
 * @time 2019/7/25 0025
 * @des
 * @function
 */
object CloseUtils {
    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    fun closeIO(vararg closeables: Closeable) {
        if (closeables == null) return
        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeables
     */
    fun closeIOQuietly(vararg closeables: Closeable) {
        if (closeables == null) return
        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (ignored: IOException) {
                }

            }
        }
    }
}