package com.fphoenixcorneae.rxretrofit.exception

import com.fphoenixcorneae.ext.loggerE
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.*
import java.text.ParseException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException

/**
 * @desc：异常处理类
 * @date：2021/4/4 14:13
 */
object ExceptionHandle {

    fun handleException(t: Throwable?): ApiException {
        var e = ApiException(Error.UNKNOWN, t)
        t?.let {
            when (it) {
                is ApiException -> {
                    e = it
                }
                is HttpException -> {
                    e = when (it.code()) {
                        Error.HTTP_UNAUTHORIZED.getCode() -> {
                            ApiException(Error.HTTP_UNAUTHORIZED, e)
                        }
                        Error.HTTP_FORBIDDEN.getCode() -> {
                            ApiException(Error.HTTP_FORBIDDEN, e)
                        }
                        Error.HTTP_NOT_FOUND.getCode() -> {
                            ApiException(Error.HTTP_NOT_FOUND, e)
                        }
                        Error.HTTP_REQUEST_TIMEOUT.getCode() -> {
                            ApiException(Error.HTTP_REQUEST_TIMEOUT, e)
                        }
                        Error.HTTP_GATEWAY_TIMEOUT.getCode() -> {
                            ApiException(Error.HTTP_GATEWAY_TIMEOUT, e)
                        }
                        Error.HTTP_INTERNAL_SERVER_ERROR.getCode() -> {
                            ApiException(Error.HTTP_INTERNAL_SERVER_ERROR, e)
                        }
                        Error.HTTP_BAD_GATEWAY.getCode() -> {
                            ApiException(Error.HTTP_BAD_GATEWAY, e)
                        }
                        Error.HTTP_SERVICE_UNAVAILABLE.getCode() -> {
                            ApiException(Error.HTTP_SERVICE_UNAVAILABLE, e)
                        }
                        else -> {
                            ApiException(it.code(), "网络错误，请稍后重试！")
                        }
                    }
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException, is NumberFormatException -> {
                    ApiException(Error.PARSE_ERROR, e)
                }
                is SocketException, is ConnectException -> {
                    ApiException(Error.NETWORK_ERROR, e)
                }
                is SocketTimeoutException, is ConnectTimeoutException -> {
                    ApiException(Error.TIMEOUT_ERROR, e)
                }
                is UnknownHostException, is UnknownServiceException -> {
                    ApiException(Error.UNKNOWN_HOST, e)
                }
                is IllegalArgumentException -> {
                    ApiException(Error.PARAMS_ERROR, e)
                }
                is SSLException, is SSLHandshakeException -> {
                    ApiException(Error.SSL_ERROR, e)
                }
                else -> {
                    ApiException(Error.UNKNOWN, e)
                }
            }
        }
        loggerE("errorCode:${e.errCode} errorMsg:${e.errorMsg}")
        return e
    }
}
