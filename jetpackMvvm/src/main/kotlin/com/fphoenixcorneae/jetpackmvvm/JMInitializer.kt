package com.fphoenixcorneae.jetpackmvvm

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.util.CoilUtils
import com.fphoenixcorneae.ext.appContext
import com.fphoenixcorneae.jetpackmvvm.base.application.ApplicationLifecycleObserver
import com.fphoenixcorneae.jetpackmvvm.network.NetworkStateReceiver
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

/**
 * @desc：Startup 初始化
 * @since：2021-04-29 17:38
 */
class JMInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        GlobalScope.launch(Dispatchers.Main) {
            context.registerReceiver(
                NetworkStateReceiver(),
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
            ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver)
        }
        GlobalScope.launch(Dispatchers.IO) {
            // MMKV 初始化
            MMKV.initialize(context)

            // 设置全局的 CoilImageLoader
            setCoilImageLoader()
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

    /**
     * 设置全局的 CoilImageLoader
     */
    private fun setCoilImageLoader() {
        Coil.setImageLoader(
            ImageLoader.Builder(appContext)
                .availableMemoryPercentage(0.25)
                .crossfade(true)
                .okHttpClient {
                    OkHttpClient.Builder()
                        .cache(CoilUtils.createDefaultCache(appContext))
                        .build()
                }
                .componentRegistry {
                    // Gif: GifDecoder 支持所有 API 级别，但速度较慢，ImageDecoderDecoder 的加载速度快，但仅在 API 28 及更高版本可用
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        add(ImageDecoderDecoder(appContext))
                    } else {
                        add(GifDecoder())
                    }
                    // Svg
                    add(SvgDecoder(appContext))
                    // Video
                    add(VideoFrameFileFetcher(appContext))
                    add(VideoFrameUriFetcher(appContext))
                    add(VideoFrameDecoder(appContext))
                }
                .build()
        )
    }
}