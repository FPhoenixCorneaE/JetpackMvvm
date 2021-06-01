package com.fphoenixcorneae.jetpackmvvm.lifecycle

import android.app.Activity
import java.util.*

/**
 * @desc：ActivityManger
 * @date：2021/06/01 11:47
 */
object ActivityManger {

    private val mActivityList = LinkedList<Activity>()

    val currentActivity: Activity?
        get() =
            if (mActivityList.isEmpty()) {
                null
            } else {
                mActivityList.last
            }

    /**
     * activity入栈
     */
    fun pushActivity(activity: Activity) {
        if (mActivityList.contains(activity)) {
            if (mActivityList.last != activity) {
                mActivityList.remove(activity)
                mActivityList.add(activity)
            }
        } else {
            mActivityList.add(activity)
        }
    }

    /**
     * activity出栈
     */
    fun popActivity(activity: Activity) {
        mActivityList.remove(activity)
    }

    /**
     * 关闭当前activity
     */
    fun finishCurrentActivity() {
        currentActivity?.finish()
    }

    /**
     * 关闭传入的activity
     */
    fun finishActivity(activity: Activity) {
        mActivityList.remove(activity)
        activity.finish()
    }

    /**
     * 关闭传入的activity类名
     */
    fun finishActivity(clazz: Class<*>) {
        for (activity in mActivityList) {
            if (activity.javaClass == clazz) {
                activity.finish()
            }
        }
    }

    /**
     * 关闭所有的activity
     */
    fun finishAllActivity() {
        for (activity in mActivityList) {
            activity.finish()
        }
    }
}