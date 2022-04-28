package com.fphoenixcorneae.jetpackmvvm.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.fphoenixcorneae.common.ext.logd

/**
 * @desc：Activity 生命周期回调
 * @since：2021-06-01 11:17
 */
class ActivityLifecycleImpl : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ActivityManger.pushActivity(activity)
        "onActivityCreated : ${activity.javaClass.canonicalName}".logd("ActivityLifecycle")
    }

    override fun onActivityStarted(activity: Activity) {
        "onActivityStarted : ${activity.javaClass.canonicalName}".logd("ActivityLifecycle")
    }

    override fun onActivityResumed(activity: Activity) {
        "onActivityResumed : ${activity.javaClass.canonicalName}".logd("ActivityLifecycle")
    }

    override fun onActivityPaused(activity: Activity) {
        "onActivityPaused : ${activity.javaClass.canonicalName}".logd("ActivityLifecycle")
    }


    override fun onActivityDestroyed(activity: Activity) {
        "onActivityDestroyed : ${activity.javaClass.canonicalName}".logd("ActivityLifecycle")
        ActivityManger.popActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        "onActivitySaveInstanceState : ${activity.javaClass.canonicalName} Bundle : $outState".logd(
            "ActivityLifecycle"
        )
    }

    override fun onActivityStopped(activity: Activity) {
        "onActivityStopped : ${activity.javaClass.canonicalName}".logd("ActivityLifecycle")
    }
}