package com.fphoenixcorneae.jetpackmvvm.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * @param position ViewBinding 在 Any 类中定义的泛型的下标
 */
fun <VB : ViewBinding> Any.getViewBinding(
    inflater: LayoutInflater,
    position: Int = 0
): VB {
    val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
    val inflate = vbClass[position].getDeclaredMethod("inflate", LayoutInflater::class.java)
    return inflate.invoke(null, inflater) as VB
}

/**
 * @param position ViewBinding 在 Any 类中定义的泛型的下标
 */
fun <VB : ViewBinding> Any.getViewBinding(
    inflater: LayoutInflater,
    container: ViewGroup?,
    attachToRoot: Boolean = false,
    position: Int = 0
): VB {
    val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
    val inflate =
        vbClass[position].getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
    return inflate.invoke(null, inflater, container, attachToRoot) as VB
}


fun <VB : ViewDataBinding> getViewBinding(vbClass: Class<VB>, parent: ViewGroup, attachToRoot: Boolean = false): VB {
    val inflate =
        vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
    return inflate.invoke(null, LayoutInflater.from(parent.context), parent, attachToRoot) as VB
}
