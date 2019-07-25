package com.longshihan.mvvmlibrary.utils

import android.content.Context
import android.os.Environment
import java.io.File
import android.os.Environment.MEDIA_MOUNTED



/**
 * Created by LONGHE001.
 * @time 2019/7/25 0025
 * @des
 * @function
 */
object FileUtils {

    fun makeDirs(file: File):File{
        if (!file.exists()){
            file.mkdirs()
        }
        return file
    }

    /**
     * String
     * 返回缓存文件夹
     */
    fun getCacheFile(context: Context): File {
        return if (Environment.getExternalStorageState() == MEDIA_MOUNTED) {
            var file: File? = null
            file = context.externalCacheDir//获取系统管理的sd卡缓存文件
            if (file == null) {//如果获取的文件为空,就使用自己定义的缓存文件夹做缓存路径
                file = File(getCacheFilePath(context))
                makeDirs(file)
            }
            file
        } else {
            context.cacheDir
        }
    }

    fun getCacheFilePath(context: Context): String {
        val packageName = context.packageName
        return "/mnt/sdcard/$packageName"
    }

}