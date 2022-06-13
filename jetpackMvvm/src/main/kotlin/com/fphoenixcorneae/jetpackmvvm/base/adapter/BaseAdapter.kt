package com.fphoenixcorneae.jetpackmvvm.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fphoenixcorneae.jetpackmvvm.ext.getViewBinding
import java.util.*

/**
 * @desc：BaseAdapter 基类
 * @date：2022/06/13 14:02
 */
abstract class BaseAdapter<T, VB : ViewDataBinding> : RecyclerView.Adapter<BaseAdapter.BindViewHolder<VB>>() {

    private var mData: List<T> = mutableListOf()

    fun setData(data: List<T>?) {
        data?.let {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mData.size
                }

                override fun getNewListSize(): Int {
                    return it.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldData: T = mData[oldItemPosition]
                    val newData: T = it[newItemPosition]
                    return this@BaseAdapter.areItemsTheSame(oldData, newData)
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldData: T = mData[oldItemPosition]
                    val newData: T = it[newItemPosition]
                    return this@BaseAdapter.areItemContentsTheSame(oldData, newData, oldItemPosition, newItemPosition)
                }
            })
            mData = data
            result.dispatchUpdatesTo(this)
        } ?: let {
            mData = mutableListOf()
            notifyItemRangeChanged(0, mData.size)
        }

    }

    fun addData(data: List<T>?, position: Int? = null) {
        if (!data.isNullOrEmpty()) {
            with(LinkedList(mData)) {
                position?.let {
                    val startPosition = when {
                        it < 0 -> 0
                        it >= size -> size
                        else -> it
                    }
                    addAll(startPosition, data)
                } ?: addAll(data)
                setData(this)
            }
        }
    }

    /**
     * 注意：子类在实现的时候根据需要改变对比的方式
     * 比如：如果泛型T不是一个基本数据类型，通常只需要对比泛型T中的唯一key就可以。
     */
    protected open fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    /**
     * 注意：子类在实现的时候根据需要改变对比的方式
     */
    protected open fun areItemContentsTheSame(
        oldItem: T,
        newItem: T,
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldItem == newItem
    }

    fun getData(): List<T> {
        return mData
    }

    fun getItem(position: Int): T {
        return mData[position]
    }

    /**
     * 如果我们在之前的数据基础上插入或者减少几条数据的话，但是又因为我们使用了DiffUtil的方式去刷新，由于之前已存在bean的数据没变，只是位置变了，
     * 所以onBindViewHolder不会执行,这个时候我们直接使用position的时候会出现位置不对问题，或者是越界的问题。
     */
    fun getActualPosition(data: T): Int {
        return mData.indexOf(data)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder<VB> {
        return with(getViewBinding<VB>(LayoutInflater.from(parent.context), parent, false, 1)) {
            setListener()
            BindViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: BindViewHolder<VB>, position: Int) {
        with(holder.binding) {
            onBindViewHolder(getItem(position), position)
            executePendingBindings()
        }
    }

    open fun VB.setListener() {}

    abstract fun VB.onBindViewHolder(data: T, position: Int)

    class BindViewHolder<VB : ViewDataBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}
