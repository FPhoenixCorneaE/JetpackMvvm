package com.fphoenixcorneae.rxretrofit.exception

/**
 * @desc：错误枚举类
 * @date：2021/4/4 14:32
 */
enum class Error(private val code: Int, private val errorMsg: String) {
    /**
     * 对应 HTTP 的状态码
     */
    HTTP_UNAUTHORIZED(401, "网络错误，请稍后重试！"),
    HTTP_FORBIDDEN(403, "网络错误，请稍后重试！"),
    HTTP_NOT_FOUND(404, "网络错误，请稍后重试！"),
    HTTP_REQUEST_TIMEOUT(408, "网络连接超时，请稍后重试！"),
    HTTP_INTERNAL_SERVER_ERROR(500, "服务器错误，请稍后重试！"),
    HTTP_BAD_GATEWAY(502, "服务器错误，请稍后重试！"),
    HTTP_SERVICE_UNAVAILABLE(503, "服务器错误，请稍后重试！"),
    HTTP_GATEWAY_TIMEOUT(504, "网络连接超时，请稍后重试！"),

    /**
     * 未知错误
     */
    UNKNOWN(1000, "请求失败，请稍后重试！"),

    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "解析错误，请稍后重试！"),

    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, "网络连接错误，请稍后重试！"),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, "证书出错，请稍后重试！"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, "网络连接超时，请稍后重试！"),

    /**
     * 参数错误
     */
    PARAMS_ERROR(1007, "参数错误，请稍后重试！"),

    /**
     * 未知主机
     */
    UNKNOWN_HOST(1008, "未知主机，请稍后重试！");

    fun getErrorMsg(): String {
        return errorMsg
    }

    fun getCode(): Int {
        return code
    }
}