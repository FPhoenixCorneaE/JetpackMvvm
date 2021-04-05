package com.fphoenixcorneae.rxretrofit.net.interceptor

import com.fphoenixcorneae.ext.loggerD
import com.fphoenixcorneae.util.AppUtil
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.EOFException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

/**
 * @desc：自定义日志拦截器，主要用于统一打印日志，修正官方提供的日志拦截器打印不连续问题。
 * @date：2021/4/4 17:49
 */
class HttpLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (!AppUtil.isDebuggable) {
            return chain.proceed(request)
        }

        val logInfo = StringBuilder("")

        val requestBody = request.body()

        val connection = chain.connection()
        val protocol = connection?.protocol() ?: Protocol.HTTP_1_1
        logInfo.append("${request.method().toUpperCase()}: ${request.url()} $protocol\n")

        val hasRequestBody = null != requestBody
        if (hasRequestBody) {
            logInfo.append("Content-Type: ${requestBody?.contentType()}\n")
            logInfo.append("Content-Length: ${requestBody?.contentLength()}\n")
        }

        var headers = request.headers()

        logInfo.append("\nRequest headers =>\n")

        var count  = headers.size()
        var i = 0
        while (i < count) {
            val name = headers.name(i)
            if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                logInfo.append("$name: ${headers.value(i)}\n")
            }
            i++
        }

        logInfo.append("\nRequest params =>\n")
        val utf8 = Charset.forName("UTF-8")

        if (hasRequestBody) {
            var charset: Charset? = null
            val buffer = Buffer()
            requestBody!!.writeTo(buffer)

            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(utf8)
            }

            if (isPlaintext(buffer)) {
                var params = buffer.readString(charset ?: utf8)
                params = URLDecoder.decode(params, "UTF-8")
                logInfo.append("$params\n")
            } else {
                logInfo.append("it is not plain text, i can't print it...\n")
            }
        } else {
            logInfo.append("Nothing...")
        }

        logInfo.append("\nResponse =>\n")

        val startNs = System.nanoTime()
        val response: Response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        logInfo.append("Total time: ${tookMs}ms\n")

        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"

        logInfo.append("Status code: ${response.code()} message: ${response.message()} body size: ${bodySize}\n")

        logInfo.append("\nResponse headers =>\n")

        headers = response.headers()
        i = 0
        count = headers.size()
        while (i < count) {
            logInfo.append("${headers.name(i)} : ${headers.value(i)}\n")
            i++
        }


        logInfo.append("\nResponse body =>\n")
        if (HttpHeaders.hasBody(response) && !bodyEncoded(response.headers())) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            var charset: Charset? = utf8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = try {
                    contentType.charset(utf8)
                } catch (e: UnsupportedCharsetException) {
                    logInfo.append("Couldn't decode the response body; charset is likely malformed.\n")
                    return response
                }
            }

            if (!isPlaintext(buffer)) {
                logInfo.append("binary " + buffer.size() + "-byte body omitted\n")
                return response
            }

            if (contentLength != 0L) {
                logInfo.append("${buffer.clone().readString(charset ?: utf8)}\n")
            }
        }

        loggerD(logInfo.toString())

        return response
    }

    private fun isPlaintext(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = if (buffer.size() < 64) buffer.size() else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            true
        } catch (e: EOFException) {
            false
        }
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }
}