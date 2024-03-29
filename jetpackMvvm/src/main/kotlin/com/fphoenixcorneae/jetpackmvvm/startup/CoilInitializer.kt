package com.fphoenixcorneae.jetpackmvvm.startup

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
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
        // dependency on CommonInitializer.
        return mutableListOf(CommonInitializer::class.java)
    }

    /**
     * 设置全局的 CoilImageLoader
     */
    private fun setCoilImageLoader() {
        Coil.setImageLoader(
            ImageLoader.Builder(applicationContext)
                .logger(DebugLogger(level = Log.DEBUG))
                .crossfade(enable = true)
                .okHttpClient {
                    OkHttpClient.Builder().build()
                }
                .memoryCache {
                    MemoryCache.Builder(applicationContext)
                        .maxSizePercent(percent = 0.25)
                        .weakReferencesEnabled(enable = true)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(directory = applicationContext.cacheDir.resolve("image_cache"))
                        .maxSizePercent(percent = 0.02)
                        .build()
                }
                .components {
                    // Gif: GifDecoder 支持所有 API 级别，但速度较慢，ImageDecoderDecoder 的加载速度快，但仅在 API 28 及更高版本可用
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                    // Svg
                    add(SvgDecoder.Factory())
                    // Video
                    add(VideoFrameDecoder.Factory())
                }
                .build()
        )
    }
}