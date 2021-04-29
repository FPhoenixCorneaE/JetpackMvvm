package com.fphoenixcorneae.jetpackmvvm.base.application

import android.app.Application
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.util.CoilUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

/**
 * @desc：提供一个很有用的功能--在 Activity/fragment 中获取 Application 级别的 ViewModel
 * @date：2021-04-11 17:27
 */
open class BaseApplication : Application(), ViewModelStoreOwner {

    private val mViewModelStore by lazy {
        ViewModelStore()
    }

    private val mViewModelFactory by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this)
    }

    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }

    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch(Dispatchers.IO) {
            // 设置全局的 CoilImageLoader
            setCoilImageLoader()
        }
    }

    /**
     * 设置全局的 CoilImageLoader
     */
    private fun setCoilImageLoader() {
        Coil.setImageLoader(
            ImageLoader.Builder(applicationContext)
                .availableMemoryPercentage(0.25)
                .crossfade(true)
                .okHttpClient {
                    OkHttpClient.Builder()
                        .cache(CoilUtils.createDefaultCache(applicationContext))
                        .build()
                }
                .componentRegistry {
                    // Gif: GifDecoder 支持所有 API 级别，但速度较慢，ImageDecoderDecoder 的加载速度快，但仅在 API 28 及更高版本可用
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        add(ImageDecoderDecoder(applicationContext))
                    } else {
                        add(GifDecoder())
                    }
                    // Svg
                    add(SvgDecoder(this@BaseApplication))
                    // Video
                    add(VideoFrameFileFetcher(this@BaseApplication))
                    add(VideoFrameUriFetcher(this@BaseApplication))
                    add(VideoFrameDecoder(this@BaseApplication))
                }
                .build()
        )
    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, mViewModelFactory)
    }
}