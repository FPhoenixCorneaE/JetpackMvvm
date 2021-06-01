package com.fphoenixcorneae.jetpackmvvm.startup

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.ProcessLifecycleOwner
import com.fphoenixcorneae.ext.logd
import com.fphoenixcorneae.jetpackmvvm.lifecycle.ActivityLifecycleImpl
import com.fphoenixcorneae.jetpackmvvm.lifecycle.ApplicationLifecycleImpl

/**
 * @desc：Startup 生命周期回调监听
 * @date：2021/06/01 16:16
 */
class LifecycleInitializer : ContentProvider() {

    override fun onCreate(): Boolean {
        val application = context!!.applicationContext as Application
        "registerActivityLifecycleCallbacks".logd("startup")
        application.registerActivityLifecycleCallbacks(ActivityLifecycleImpl())
        "Add ApplicationLifecycleObserver".logd("startup")
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleImpl)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }
}