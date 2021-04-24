package com.fphoenixcorneae.rxretrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @desc：头部参数拦截器，传入 headers
 * @date：2021/4/4 15:33
 */
class HeaderInterceptor(
    private val headers: Map<String, String>? = null
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()
        if (headers != null && headers.isNotEmpty()) {
            val keys = headers.keys
            for (headerKey in keys) {
                headers[headerKey]?.let {
                    builder.addHeader(headerKey, it).build()
                }
            }
        }
        builder.addHeaders()
        // 请求信息
        return chain.proceed(builder.build())
    }

    private fun Request.Builder.addHeaders(): Request.Builder {
        return addHeader("Content_Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("charset", "UTF-8")
            .addHeader("Connection", "close")
    }
}