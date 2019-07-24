package com.longshihan.mvvmlibrary.base

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Messenger
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import com.longshihan.mvvmlibrary.ContainerActivity
import java.lang.reflect.ParameterizedType

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), IBaseView {
    protected var viewModel: BaseViewModel? = null
    var dialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
        if (viewModel == null) {
            viewModel = createViewModel(this, BaseViewModel::class.java)
        }
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel!!)
        //页面接受的参数方法
        initParam()
        //页面数据初始化方法
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
        //注册RxBus
        viewModel!!.registerRxBus()
    }

    abstract fun initViewModel(): BaseViewModel


    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    fun startContainerActivity(canonicalName: String) {
        startContainerActivity(canonicalName, null)
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    fun startContainerActivity(canonicalName: String, bundle: Bundle?) {
        val intent = Intent(this, ContainerActivity::class.java)
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName)
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle)
        }
        startActivity(intent)
    }
    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T : ViewModel> createViewModel(activity: FragmentActivity, cls: Class<T>): T {
        return ViewModelProviders.of(activity).get(cls)
    }

    override fun onDestroy() {
        super.onDestroy()
        //解除ViewModel生命周期感应
        lifecycle.removeObserver(viewModel!!)
        viewModel?.removeRxBus()
    }


}