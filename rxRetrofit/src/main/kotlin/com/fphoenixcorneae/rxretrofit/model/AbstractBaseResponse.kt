package com.fphoenixcorneae.rxretrofit.model

/**
 * @desc：服务器返回数据的基类
 *       如果需要框架帮你做脱壳处理请继承它！！
 *       请注意：必须实现抽象方法，根据自己的业务判断返回请求结果是否成功。
 * @date：2021/4/4 13:56
 */
abstract class AbstractBaseResponse<T> {

    /**
     * 抽象方法，用户的基类继承该类时，需要重写该方法
     */
    abstract fun isSuccess(): Boolean

    /**
     * 抽象方法，用户的基类继承该类时，需要重写该方法
     */
    abstract fun getResponseData(): T

    /**
     * 抽象方法，用户的基类继承该类时，需要重写该方法
     */
    abstract fun getResponseCode(): Int

    /**
     * 抽象方法，用户的基类继承该类时，需要重写该方法
     */
    abstract fun getResponseMsg(): String
}