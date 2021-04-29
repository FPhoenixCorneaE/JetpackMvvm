/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fphoenixcorneae.jetpackmvvm.navigation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.*
import androidx.navigation.NavDestination.ClassType
import com.fphoenixcorneae.jetpackmvvm.R
import com.fphoenixcorneae.jetpackmvvm.navigation.DialogFragmentNavigator.Destination

/**
 * Navigator that uses [DialogFragment.show]. Every
 * destination using this Navigator must set a valid DialogFragment class name with
 * `android:name` or [Destination.setClassName].
 */
@Navigator.Name("dialog")
class DialogFragmentNavigator(
    private val mContext: Context,
    private val mFragmentManager: FragmentManager
) : Navigator<Destination>() {
    private var mDialogCount = 0
    private val mObserver = LifecycleEventObserver { source, event ->
        if (event == Lifecycle.Event.ON_STOP) {
            val dialogFragment = source as DialogFragment
            if (!dialogFragment.requireDialog().isShowing) {
                NavHostFragment.findNavController(dialogFragment).popBackStack()
            }
        }
    }

    override fun popBackStack(): Boolean {
        if (mDialogCount == 0) {
            return false
        }
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring popBackStack() call: FragmentManager has already"
                + " saved its state"
            )
            return false
        }
        val existingFragment = mFragmentManager
            .findFragmentByTag(DIALOG_TAG + --mDialogCount)
        if (existingFragment != null) {
            existingFragment.lifecycle.removeObserver(mObserver)
            (existingFragment as DialogFragment).dismiss()
        }
        return true
    }

    override fun createDestination(): Destination {
        return Destination(this)
    }

    override fun navigate(
        destination: Destination, args: Bundle?,
        navOptions: NavOptions?, navigatorExtras: Extras?
    ): NavDestination? {
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already"
                + " saved its state"
            )
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = mContext.packageName + className
        }
        val frag = mFragmentManager.fragmentFactory.instantiate(
            mContext.classLoader, className
        )
        require(DialogFragment::class.java.isAssignableFrom(frag.javaClass)) {
            ("Dialog destination " + destination.className
                + " is not an instance of DialogFragment")
        }
        val dialogFragment = frag as DialogFragment
        dialogFragment.arguments = args
        dialogFragment.lifecycle.addObserver(mObserver)
        dialogFragment.show(mFragmentManager, DIALOG_TAG + mDialogCount++)
        return destination
    }

    override fun onSaveState(): Bundle? {
        if (mDialogCount == 0) {
            return null
        }
        val b = Bundle()
        b.putInt(KEY_DIALOG_COUNT, mDialogCount)
        return b
    }

    override fun onRestoreState(savedState: Bundle) {
        mDialogCount = savedState.getInt(KEY_DIALOG_COUNT, 0)
        for (index in 0 until mDialogCount) {
            val fragment = mFragmentManager
                .findFragmentByTag(DIALOG_TAG + index) as DialogFragment?
            if (fragment != null) {
                fragment.lifecycle.addObserver(mObserver)
            } else {
                throw IllegalStateException(
                    "DialogFragment " + index
                        + " doesn't exist in the FragmentManager"
                )
            }
        }
    }

    /**
     * NavDestination specific to [DialogFragmentNavigator].
     */
    @ClassType(DialogFragment::class)
    class Destination
    /**
     * Construct a new fragment destination. This destination is not valid until you set the
     * Fragment via [.setClassName].
     *
     * @param fragmentNavigator The [DialogFragmentNavigator] which this destination
     * will be associated with. Generally retrieved via a
     * [NavController]'s
     * [NavigatorProvider.getNavigator] method.
     */
    (fragmentNavigator: Navigator<out Destination?>) : NavDestination(fragmentNavigator), FloatingWindow {
        private var mClassName: String? = null

        /**
         * Construct a new fragment destination. This destination is not valid until you set the
         * Fragment via [.setClassName].
         *
         * @param navigatorProvider The [NavController] which this destination
         * will be associated with.
         */
        constructor(navigatorProvider: NavigatorProvider) : this(navigatorProvider.getNavigator<DialogFragmentNavigator>(DialogFragmentNavigator::class.java)) {}

        @CallSuper
        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)
            val a = context.resources.obtainAttributes(
                attrs,
                R.styleable.DialogFragmentNavigator
            )
            val className = a.getString(R.styleable.DialogFragmentNavigator_android_name)
            className?.let { setClassName(it) }
            a.recycle()
        }

        /**
         * Set the DialogFragment class name associated with this destination
         *
         * @param className The class name of the DialogFragment to show when you navigate to this
         * destination
         * @return this [Destination]
         */
        fun setClassName(className: String): Destination {
            mClassName = className
            return this
        }

        /**
         * Gets the DialogFragment's class name associated with this destination
         *
         * @throws IllegalStateException when no DialogFragment class was set.
         */
        val className: String
            get() {
                checkNotNull(mClassName) { "DialogFragment class was not set" }
                return mClassName!!
            }
    }

    companion object {
        private const val TAG = "DialogFragmentNavigator"
        private const val KEY_DIALOG_COUNT = "androidx-nav-dialogfragment:navigator:count"
        private const val DIALOG_TAG = "androidx-nav-fragment:navigator:dialog:"
    }
}