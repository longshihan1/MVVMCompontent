package com.longshihan.mvvmlibrary.intergration

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class ActivityLifecycle : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityDestroyed(p0: Activity) {
        AppManager.removeActivity(p0)
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        AppManager.addActivity(p0)
    }

}