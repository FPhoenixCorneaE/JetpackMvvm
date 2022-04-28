package com.fphoenixcorneae.jetpackmvvm.startup

import android.content.Context
import android.os.Build
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
import com.fphoenixcorneae.common.CommonInitializer
import com.fphoenixcorneae.common.ext.applicationContext
import com.fphoenixcorneae.common.ext.logd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

/**
 * @desc：Startup 初始化 Coil
 * @since：2021-05-26 14:40
 */
class CoilInitializer : Initializer<Unit>, CoroutineScope by MainScope() {

    override fun create(context: Context) {
        launch(Dispatchers.IO) {
            // 设置全局的 CoilImageLoader
            "Coil 初始化".logd("startup")
            setCoilImageLoader()
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return mutableListOf(CommonInitializer::class.java)
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
                    add(SvgDecoder(applicationContext))
                    // Video
                    add(VideoFrameFileFetcher(applicationContext))
                    add(VideoFrameUriFetcher(applicationContext))
                    add(VideoFrameDecoder(applicationContext))
                }
                .build()
        )
    }
}