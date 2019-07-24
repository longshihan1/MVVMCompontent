package com.longshihan.mvvmlibrary.base

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
interface IModel {
    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
     fun onCleared()
}