package com.fphoenixcorneae.jetpackmvvm

import android.content.ContentProvider
import android.content.ContentValues
import android.content.IntentFilter
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.Uri
import androidx.lifecycle.ProcessLifecycleOwner
import com.fphoenixcorneae.jetpackmvvm.base.application.ApplicationLifecycleObserver
import com.fphoenixcorneae.jetpackmvvm.network.NetworkStateReceiver

/**
 * @desc：内容提供者
 * @date：2021/04/29 13:38
 */
class JMContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        context?.registerReceiver(NetworkStateReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}