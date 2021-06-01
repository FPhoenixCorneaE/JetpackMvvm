package com.fphoenixcorneae.jetpackmvvm.startup

import android.content.ContentProvider
import android.content.ContentValues
import android.content.IntentFilter
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.Uri
import com.fphoenixcorneae.jetpackmvvm.network.NetworkStateReceiver

/**
 * @desc：Startup 网络变化监听
 * @date：2021/06/01 16:45
 */
class NetworkChangedInitializer : ContentProvider() {

    override fun onCreate(): Boolean {
        context?.registerReceiver(
            NetworkStateReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
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