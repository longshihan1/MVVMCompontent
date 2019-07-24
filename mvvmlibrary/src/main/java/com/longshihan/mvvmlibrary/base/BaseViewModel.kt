package com.longshihan.mvvmlibrary.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import com.longshihan.mvvmlibrary.bus.event.SingleLiveEvent
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.lang.ref.WeakReference

/**
 * Created by LONGHE001.
 *
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class BaseViewModel<M : BaseModel> @JvmOverloads constructor(application: Application, model1: M? = null) :
    AndroidViewModel(application), IBaseViewModel, Consumer<Disposable> {
    var model: M? = null
    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private var mCompositeDisposable: CompositeDisposable? = null
    //弱引用持有
    var lifecycle: WeakReference<LifecycleProvider<*>>? = null

    init {
        this.model = model1
        mCompositeDisposable = CompositeDisposable()
    }


    fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    fun injectLifecycleProvider(lifecycle: LifecycleProvider<*>) {
        this.lifecycle = WeakReference(lifecycle)
    }

    fun getLifecycleProvider(): LifecycleProvider<*>? {
        return lifecycle!!.get()
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    @Throws(Exception::class)
    override fun accept(disposable: Disposable) {

    }

}
