package com.fphoenixcorneae.rxretrofit.net

import com.fphoenixcorneae.rxretrofit.net.factory.CoroutinesCallAdapterFactory
import com.fphoenixcorneae.rxretrofit.net.interceptor.CacheInterceptor
import com.fphoenixcorneae.rxretrofit.net.interceptor.HeaderInterceptor
import com.fphoenixcorneae.rxretrofit.net.interceptor.HttpLoggingInterceptor
import com.fphoenixcorneae.util.ContextUtil
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @desc：网络请求构建器基类
 * @date：2021/4/4 17:52
 */
abstract class AbstractBaseNetworkApi {

    /**
     * 获取 ServiceApi
     */
    fun <T> getApi(serviceClass: Class<T>, baseUrl: String): T {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .setRetrofitBuilder()
                .build()
                .create(serviceClass)
    }

    /**
     * 重写父类的 setHttpClientBuilder 方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    fun OkHttpClient.Builder.setHttpClientBuilder(): OkHttpClient.Builder {
        return apply {
            // 设置缓存配置 缓存最大10M
            cache(Cache(File(ContextUtil.context.cacheDir, NET_CACHE_DIR), NET_CACHE_MAX_SIZE))
            // 添加Cookies自动持久化
            cookieJar(cookieJar)
            // 添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            addInterceptor(HeaderInterceptor(mapOf()))
            // 添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            // 日志拦截器
            addInterceptor(HttpLoggingInterceptor())
            // 超时时间 连接、读、写
            connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
        }
    }

    /**
     * 重写父类的 setRetrofitBuilder 方法，
     * 在这里可以对 Retrofit.Builder 做任意操作，比如添加 GSON 解析器，Protocol
     */
    fun Retrofit.Builder.setRetrofitBuilder(): Retrofit.Builder {
        return apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            addCallAdapterFactory(CoroutinesCallAdapterFactory())
        }
    }

    /**
     * 配置 http
     */
    private val okHttpClient: OkHttpClient
        get() {
            return RetrofitUrlManager
                    .getInstance()
                    .with(OkHttpClient.Builder())
                    .setHttpClientBuilder()
                    .build()
        }

    /**
     * Cookies 自动持久化
     */
    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(ContextUtil.context))
    }

    companion object {
        const val TIMEOUT_CONNECT = 10L
        const val TIMEOUT_READ = 10L
        const val TIMEOUT_WRITE = 10L
        const val NET_CACHE_DIR = "net_cache"
        const val NET_CACHE_MAX_SIZE = 10 * 1024 * 1024L
    }
}



