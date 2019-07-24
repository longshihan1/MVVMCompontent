package com.longshihan.mvvmlibrary.intergration

import android.app.Activity
import java.util.*

/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
object AppManager {
    //管理所有activity
    var mActivityList: Stack<Activity> = Stack()

    /**
     * 获取最近启动的一个 [Activity], 此方法不保证获取到的 [Activity] 正处于前台可见状态
     * 即使 App 进入后台或在这个 [Activity] 中打开一个之前已经存在的 [Activity], 这时调用此方法
     * 还是会返回这个最近启动的 [Activity], 因此基本不会出现 `null` 的情况
     * 比较适合大部分的使用场景, 如 startActivity
     *
     *
     * Tips: mActivityList 容器中的顺序仅仅是 Activity 的创建顺序, 并不能保证和 Activity 任务栈顺序一致
     *
     * @return
     */
    fun getTopActivity(): Activity? {
        return if (mActivityList.size > 0) mActivityList.lastElement() else null
    }

    /**
     * 释放资源
     */
    fun release() {
        mActivityList.clear()
        mActivityList = Stack()
    }


    /**
     * 获取在前台的 [Activity] (保证获取到的 [Activity] 正处于可见状态, 即未调用 [Activity.onStop]), 获取的 [Activity] 存续时间
     * 是在 [Activity.onStop] 之前, 所以如果当此 [Activity] 调用 [Activity.onStop] 方法之后, 没有其他的 [Activity] 回到前台(用户返回桌面或者打开了其他 App 会出现此状况)
     * 这时调用 [.getCurrentActivity] 有可能返回 `null`, 所以请注意使用场景和 [.getTopActivity] 不一样
     *
     *
     * Example usage:
     * 使用场景比较适合, 只需要在可见状态的 [Activity] 上执行的操作
     * 如当后台 [Service] 执行某个任务时, 需要让前台 [Activity] ,做出某种响应操作或其他操作,如弹出 [Dialog], 这时在 [Service] 中就可以使用 [.getCurrentActivity]
     * 如果返回为 `null`, 说明没有前台 [Activity] (用户返回桌面或者打开了其他 App 会出现此状况), 则不做任何操作, 不为 `null`, 则弹出 [Dialog]
     *
     * @return
     */
    fun getCurrentActivity(): Activity? {
        return if (!mActivityList.empty())
            mActivityList.lastElement()
        else
            null
    }



    /**
     * 添加Activity到集合
     */
    fun addActivity(activity: Activity) {
        synchronized(AppManager::class.java) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity)
            }
        }
    }

    /**
     * 删除集合里的指定activity
     *
     * @param activity
     */
    fun removeActivity(activity: Activity) {
        activity.finish()
        synchronized(AppManager::class.java) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity)
            }
        }
    }

    /**
     * 删除集合里的指定位置的activity
     *
     * @param location
     */
    fun removeActivity(location: Int): Activity? {
        synchronized(AppManager::class.java) {
            if (location > 0 && location < mActivityList.size) {
                return mActivityList.removeAt(location)
            }
        }
        return null
    }

    /**
     * 关闭指定activity
     *
     * @param activityClass
     */
    fun killActivity(activityClass: Class<*>) {
        for (activity in mActivityList) {
            if (activity.javaClass == activityClass) {
                activity.finish()
            }
        }
    }


    /**
     * 指定的activity实例是否存活
     *
     * @param activity
     * @return
     */
    fun activityInstanceIsLive(activity: Activity): Boolean {
        return mActivityList.contains(activity)
    }


    /**
     * 指定的activity class是否存活(一个activity可能有多个实例)
     *
     * @param activityClass
     * @return
     */
    fun activityClassIsLive(activityClass: Class<*>): Boolean {
        if (mActivityList == null) {
            return false
        }
        for (activity in mActivityList) {
            if (activity.javaClass == activityClass) {
                return true
            }
        }

        return false
    }


    /**
     * 关闭所有activity
     */
    fun killAll() {
        val iterator = mActivityList.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            iterator.remove()
            next.finish()
        }

    }

    /**
     * 关闭所有 [Activity],排除指定的 [Activity]
     *
     * @param excludeActivityClasses activity class
     */
    fun killAll(vararg excludeActivityClasses: Class<*>) {
        val excludeList = excludeActivityClasses.asList()
        synchronized(AppManager::class.java) {
            val iterator = mActivityList.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (excludeList.contains(next.javaClass))
                    continue

                iterator.remove()
                next.finish()
            }
        }
    }

    /**
     * 关闭所有 [Activity],排除指定的 [Activity]
     *
     * @param excludeActivityName [Activity] 的完整全路径
     */
    fun killAll(vararg excludeActivityName: String) {
        val excludeList:List<String> = excludeActivityName.asList()
        synchronized(AppManager::class.java) {
            val iterator = mActivityList.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (excludeList.contains(next.javaClass.name)) {
                    continue
                }
                iterator.remove()
                next.finish()
            }
        }
    }


    /**
     * 退出应用程序
     */
    fun appExit() {
        try {
            killAll()
            mActivityList= Stack()
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}