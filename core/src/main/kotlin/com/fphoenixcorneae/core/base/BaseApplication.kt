package com.fphoenixcorneae.core.base

import android.app.Application
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.util.CoilUtils
import okhttp3.OkHttpClient

/**
 * @desc：提供一个很有用的功能--在 Activity/fragment 中获取 Application 级别的 ViewModel
 * @date：2021-04-11 17:27
 */
open class BaseApplication : Application(), ViewModelStoreOwner, ImageLoaderFactory {

    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
                .crossfade(true)
                .okHttpClient {
                    OkHttpClient.Builder()
                            .cache(CoilUtils.createDefaultCache(applicationContext))
                            .build()
                }
                .componentRegistry {
                    // Gif
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        add(ImageDecoderDecoder())
                    } else {
                        add(GifDecoder())
                    }
                    // Svg
                    add(SvgDecoder(this@BaseApplication))
                    // Video
                    add(VideoFrameFileFetcher(this@BaseApplication))
                    add(VideoFrameUriFetcher(this@BaseApplication))
                }
                .build()
    }
}