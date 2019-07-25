package com.longshihan.mvvmlibrary.base

interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    abstract fun initParam()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化界面观察者的监听
     */
    abstract fun initViewObservable()

    abstract fun initViewModel(): BaseViewModel?
}