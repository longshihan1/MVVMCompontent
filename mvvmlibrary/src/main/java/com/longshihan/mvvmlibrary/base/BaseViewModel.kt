package com.longshihan.mvvmlibrary.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.longshihan.mvvmlibrary.bus.event.SingleLiveEvent
import com.longshihan.mvvmlibrary.bus.event.UIChangeLiveData
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.lang.ref.WeakReference
import java.util.HashMap

/**
 * Created by LONGHE001.
 *
 * @time 2019/7/24 0024
 * @des
 * @function
 */
abstract class BaseViewModel @JvmOverloads constructor(application: Application) :
    AndroidViewModel(application), IBaseViewModel, Consumer<Disposable>, IModel {
    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private var mCompositeDisposable: CompositeDisposable? = null
    //弱引用持有
    var uc: UIChangeLiveData? = null

    init {
        mCompositeDisposable = CompositeDisposable()
    }


    fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }


    fun getUC(): UIChangeLiveData {
        if (uc == null) {
            uc = UIChangeLiveData()
        }
        return uc!!
    }

    fun showDialog() {
        showDialog("请稍后...")
    }

    fun showDialog(title: String) {
        uc!!.showDialogEvent.postValue(title)
    }

    fun dismissDialog() {
        uc!!.dismissDialogEvent.call()
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<*>) {
        startActivity(clz, null)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val params = HashMap<String, Any>()
        params[ParameterField.CLASS] = clz
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        uc!!.startActivityEvent.postValue(params)
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
        val params = HashMap<String, Any>()
        params[ParameterField.CANONICAL_NAME] = canonicalName
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        uc!!.startContainerActivityEvent.postValue(params)
    }

    /**
     * 关闭界面
     */
    fun finish() {
        uc!!.finishEvent.call()
    }

    /**
     * 返回上一层
     */
    fun onBackPressed() {
        uc!!.onBackPressedEvent.call()
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {}

    override fun onCreate() {}

    override fun onDestroy() {}

    override fun onStart() {}

    override fun onStop() {}

    override fun onResume() {}

    override fun onPause() {}

    fun registerRxBus() {}

    fun removeRxBus() {}

    override fun onCleared() {
        super.onCleared()
    }

    object ParameterField {
        var CLASS = "CLASS"
        var CANONICAL_NAME = "CANONICAL_NAME"
        var BUNDLE = "BUNDLE"
    }
}
