package com.fphoenixcorneae.jetpackmvvm.livedata

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import com.fphoenixcorneae.common.ext.applicationContext
import com.fphoenixcorneae.common.ext.connectivityManager

/**
 * @desc：网络连接状态实时数据
 * @date：2021/09/15 15:39
 */
@SuppressLint("MissingPermission", "ObsoleteSdkInt")
class NetworkStateLiveData : LiveData<Boolean>() {

    /** 上下文 */
    private val mContext = applicationContext

    /** 连接管理器 */
    private val mConnectivityManager = mContext.connectivityManager

    /** 网络回调 */
    private val mNetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                postValue(true)
            }

            override fun onLost(network: Network) {
                postValue(false)
            }
        }
    }

    /** 网络广播接收器 */
    private val mNetworkBroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // 更新网络状态
                updateNetworkState()
            }
        }
    }

    override fun onActive() {
        super.onActive()
        // 更新网络状态
        updateNetworkState()
        // 注册的观察者
        registerObserver()
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mConnectivityManager?.unregisterNetworkCallback(mNetworkCallback)
        } else {
            mContext.unregisterReceiver(mNetworkBroadcastReceiver)
        }
    }

    /**
     * 注册观察者
     */
    private fun registerObserver() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                mConnectivityManager?.registerDefaultNetworkCallback(mNetworkCallback)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                registerLollipopNetworkAvailableRequest()
            }
            else -> {
                mContext.registerReceiver(
                    mNetworkBroadcastReceiver,
                    IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }
    }

    private fun registerLollipopNetworkAvailableRequest() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(android.net.NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        mConnectivityManager?.registerNetworkCallback(networkRequest, mNetworkCallback)
    }

    /**
     * 更新网络状态
     */
    private fun updateNetworkState() {
        val activeNetwork = mConnectivityManager?.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }
}