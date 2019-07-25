package com.longshihan.mvvmlibrary.utils.compression

import java.io.File

/**
 * Created by LONGHE001.
 * @time 2019/7/25 0025
 * @des
 * @function
 */
interface OnCompressListener {
    /**
     * Fired when the compression is started, override to handle in your own code
     */
    abstract fun onStart()

    /**
     * Fired when a compression returns successfully, override to handle in your own code
     */
    abstract fun onSuccess(file: File?)

    /**
     * Fired when a compression fails to complete, override to handle in your own code
     */
    abstract fun onError(e: Throwable)
}