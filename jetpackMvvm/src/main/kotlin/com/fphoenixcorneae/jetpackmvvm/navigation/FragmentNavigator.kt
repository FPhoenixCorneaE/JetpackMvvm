/*
 * Copyright (C) 2017 The Android Open Source Project
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
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.*
import androidx.navigation.NavDestination.ClassType
import com.fphoenixcorneae.jetpackmvvm.R
import com.fphoenixcorneae.jetpackmvvm.navigation.FragmentNavigator.Destination
import java.util.*

/**
 * Navigator that navigates through [fragment transactions][androidx.fragment.app.FragmentTransaction]. Every
 * destination using this Navigator must set a valid Fragment class name with
 * `android:name` or [Destination.setClassName].
 *
 *
 * The current Fragment from FragmentNavigator's perspective can be retrieved by calling
 * [FragmentManager.getPrimaryNavigationFragment] with the FragmentManager
 * passed to this FragmentNavigator.
 *
 *
 * Note that the default implementation does Fragment transactions
 * asynchronously, so the current Fragment will not be available immediately
 * (i.e., in callbacks to [NavController.OnDestinationChangedListener]).
 */
@Navigator.Name("fragment")
class FragmentNavigator(
    private val mContext: Context, private val mFragmentManager: FragmentManager,
    private val mContainerId: Int
) : Navigator<Destination>() {
    private val mBackStack = ArrayDeque<Int>()

    /**
     * {@inheritDoc}
     *
     *
     * This method must call
     * [androidx.fragment.app.FragmentTransaction.setPrimaryNavigationFragment]
     * if the pop succeeded so that the newly visible Fragment can be retrieved with
     * [FragmentManager.getPrimaryNavigationFragment].
     *
     *
     * Note that the default implementation pops the Fragment
     * asynchronously, so the newly visible Fragment from the back stack
     * is not instantly available after this call completes.
     */
    override fun popBackStack(): Boolean {
        if (mBackStack.isEmpty()) {
            return false
        }
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring popBackStack() call: FragmentManager has already"
                + " saved its state"
            )
            return false
        }
        mFragmentManager.popBackStack(
            generateBackStackName(mBackStack.size, mBackStack.peekLast()),
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        mFragmentManager.fragments.removeAt(mBackStack.size - 1)
        mBackStack.removeLast()
        return true
    }

    override fun createDestination(): Destination {
        return Destination(this)
    }

    /**
     * Instantiates the Fragment via the FragmentManager's
     * [androidx.fragment.app.FragmentFactory].
     *
     *
     * Note that this method is **not** responsible for calling
     * [Fragment.setArguments] on the returned Fragment instance.
     *
     * @param context         Context providing the correct [ClassLoader]
     * @param fragmentManager FragmentManager the Fragment will be added to
     * @param className       The Fragment to instantiate
     * @param args            The Fragment's arguments, if any
     * @return A new fragment instance.
     */
    @Deprecated(
        """Set a custom {@link FragmentFactory} via
      {@link FragmentManager#setFragmentFactory(FragmentFactory)} to control
      instantiation of Fragments.""", ReplaceWith("fragmentManager.fragmentFactory.instantiate(context.classLoader, className)")
    )
    fun instantiateFragment(
        context: Context,
        fragmentManager: FragmentManager,
        className: String, args: Bundle?
    ): Fragment {
        return fragmentManager.fragmentFactory.instantiate(
            context.classLoader, className
        )
    }

    /**
     * {@inheritDoc}
     *
     *
     * This method should always call
     * [androidx.fragment.app.FragmentTransaction.setPrimaryNavigationFragment]
     * so that the Fragment associated with the new destination can be retrieved with
     * [FragmentManager.getPrimaryNavigationFragment].
     *
     *
     * Note that the default implementation commits the new Fragment
     * asynchronously, so the new Fragment is not instantly available
     * after this call completes.
     */
    override fun navigate(
        destination: Destination, args: Bundle?,
        navOptions: NavOptions?, navigatorExtras: Navigator.Extras?
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
        val frag = instantiateFragment(
            mContext, mFragmentManager,
            className, args
        )
        frag.arguments = args
        val ft = mFragmentManager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }
        if (mBackStack.size > 0) {
            ft.hide(mFragmentManager.fragments[mBackStack.size - 1])
            ft.add(mContainerId, frag)
        } else {
            ft.replace(mContainerId, frag)
        }

        //        ft.replace(mContainerId, frag);
        ft.setPrimaryNavigationFragment(frag)
        @IdRes val destId = destination.id
        val initialNavigation = mBackStack.isEmpty()
        // Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
            && navOptions.shouldLaunchSingleTop()
            && mBackStack.peekLast() == destId)
        val isAdded: Boolean
        isAdded = if (initialNavigation) {
            true
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                mFragmentManager.popBackStack(
                    generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
            }
            false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
            true
        }
        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key!!, value!!)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        // The commit succeeded, update our view of the world
        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        }
    }

    override fun onSaveState(): Bundle {
        val b = Bundle()
        val backStack = IntArray(mBackStack.size)
        var index = 0
        for (id in mBackStack) {
            backStack[index++] = id
        }
        b.putIntArray(KEY_BACK_STACK_IDS, backStack)
        return b
    }

    override fun onRestoreState(savedState: Bundle) {
        val backStack = savedState.getIntArray(KEY_BACK_STACK_IDS)
        if (backStack != null) {
            mBackStack.clear()
            for (destId in backStack) {
                mBackStack.add(destId)
            }
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }

    private fun getDestId(backStackName: String?): Int {
        val split: Array<String> = backStackName?.split("-".toRegex())
            ?.toTypedArray() ?: arrayOf()
        check(split.size == 2) {
            ("Invalid back stack entry on the "
                + "NavHostFragment's back stack - use getChildFragmentManager() "
                + "if you need to do custom FragmentTransactions from within "
                + "Fragments created via your navigation graph.")
        }
        return try {
            // Just make sure the backStackIndex is correctly formatted
            split[0].toInt()
            split[1].toInt()
        } catch (e: NumberFormatException) {
            throw IllegalStateException(
                "Invalid back stack entry on the "
                    + "NavHostFragment's back stack - use getChildFragmentManager() "
                    + "if you need to do custom FragmentTransactions from within "
                    + "Fragments created via your navigation graph."
            )
        }
    }

    /**
     * NavDestination specific to [FragmentNavigator]
     */
    @ClassType(Fragment::class)
    class Destination
    /**
     * Construct a new fragment destination. This destination is not valid until you set the
     * Fragment via [.setClassName].
     *
     * @param fragmentNavigator The [FragmentNavigator] which this destination
     * will be associated with. Generally retrieved via a
     * [NavController]'s
     * [NavigatorProvider.getNavigator] method.
     */
    (fragmentNavigator: Navigator<out Destination?>) : NavDestination(fragmentNavigator) {
        private var mClassName: String? = null

        /**
         * Construct a new fragment destination. This destination is not valid until you set the
         * Fragment via [.setClassName].
         *
         * @param navigatorProvider The [NavController] which this destination
         * will be associated with.
         */
        constructor(navigatorProvider: NavigatorProvider) : this(navigatorProvider.getNavigator<FragmentNavigator>(FragmentNavigator::class.java)) {}

        @CallSuper
        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)
            val a = context.resources.obtainAttributes(
                attrs,
                R.styleable.FragmentNavigator
            )
            val className = a.getString(R.styleable.FragmentNavigator_android_name)
            className?.let { setClassName(it) }
            a.recycle()
        }

        /**
         * Set the Fragment class name associated with this destination
         *
         * @param className The class name of the Fragment to show when you navigate to this
         * destination
         * @return this [Destination]
         */
        fun setClassName(className: String): Destination {
            mClassName = className
            return this
        }

        /**
         * Gets the Fragment's class name associated with this destination
         *
         * @throws IllegalStateException when no Fragment class was set.
         */
        val className: String
            get() {
                checkNotNull(mClassName) { "Fragment class was not set" }
                return mClassName!!
            }

        override fun toString(): String {
            val sb = StringBuilder()
            sb.append(super.toString())
            sb.append(" class=")
            if (mClassName == null) {
                sb.append("null")
            } else {
                sb.append(mClassName)
            }
            return sb.toString()
        }
    }

    /**
     * Extras that can be passed to FragmentNavigator to enable Fragment specific behavior
     */
    class Extras internal constructor(sharedElements: Map<View?, String?>) : Navigator.Extras {
        private val mSharedElements = LinkedHashMap<View?, String?>()

        /**
         * Gets the map of shared elements associated with these Extras. The returned map
         * is an [unmodifiable][Collections.unmodifiableMap] copy of the underlying
         * map and should be treated as immutable.
         */
        val sharedElements: Map<View?, String?>
            get() = Collections.unmodifiableMap(mSharedElements)

        /**
         * Builder for constructing new [Extras] instances. The resulting instances are
         * immutable.
         */
        class Builder {
            private val mSharedElements = LinkedHashMap<View?, String?>()

            /**
             * Adds multiple shared elements for mapping Views in the current Fragment to
             * transitionNames in the Fragment being navigated to.
             *
             * @param sharedElements Shared element pairs to add
             * @return this [Builder]
             */
            fun addSharedElements(sharedElements: Map<View?, String?>): Builder {
                for ((view, name) in sharedElements) {
                    if (view != null && name != null) {
                        addSharedElement(view, name)
                    }
                }
                return this
            }

            /**
             * Maps the given View in the current Fragment to the given transition name in the
             * Fragment being navigated to.
             *
             * @param sharedElement A View in the current Fragment to match with a View in the
             * Fragment being navigated to.
             * @param name          The transitionName of the View in the Fragment being navigated to that
             * should be matched to the shared element.
             * @return this [Builder]
             * @see [androidx.fragment.app.FragmentTransaction.addSharedElement]
             */
            fun addSharedElement(sharedElement: View, name: String): Builder {
                mSharedElements[sharedElement] = name
                return this
            }

            /**
             * Constructs the final [Extras] instance.
             *
             * @return An immutable [Extras] instance.
             */
            fun build(): Extras {
                return Extras(mSharedElements)
            }
        }

        init {
            mSharedElements.putAll(sharedElements)
        }
    }

    companion object {
        private const val TAG = "FragmentNavigator"
        private const val KEY_BACK_STACK_IDS = "androidx-nav-fragment:navigator:backStackIds"
    }
}