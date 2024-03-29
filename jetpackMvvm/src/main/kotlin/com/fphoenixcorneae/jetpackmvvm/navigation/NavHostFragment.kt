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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.*
import com.fphoenixcorneae.jetpackmvvm.R

/**
 * NavHostFragment provides an area within your layout for self-contained navigation to occur.
 *
 *
 * NavHostFragment is intended to be used as the content area within a layout resource
 * defining your app's chrome around it, e.g.:
 *
 * <pre class="prettyprint">
 * &lt;androidx.drawerlayout.widget.DrawerLayout
 * xmlns:android="http://schemas.android.com/apk/res/android"
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"&gt;
 * &lt;fragment
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:id="@+id/my_nav_host_fragment"
 * android:name="androidx.navigation.fragment.NavHostFragment"
 * app:navGraph="@navigation/nav_sample"
 * app:defaultNavHost="true" /&gt;
 * &lt;android.support.design.widget.NavigationView
 * android:layout_width="wrap_content"
 * android:layout_height="match_parent"
 * android:layout_gravity="start"/&gt;
 * &lt;/androidx.drawerlayout.widget.DrawerLayout&gt;
</pre> *
 *
 *
 * Each NavHostFragment has a [NavController] that defines valid navigation within
 * the navigation host. This includes the [navigation graph][NavGraph] as well as navigation
 * state such as current location and back stack that will be saved and restored along with the
 * NavHostFragment itself.
 *
 *
 * NavHostFragments register their navigation controller at the root of their view subtree
 * such that any descendant can obtain the controller instance through the [Navigation]
 * helper class's methods such as [Navigation.findNavController]. View event listener
 * implementations such as [View.OnClickListener] within navigation destination
 * fragments can use these helpers to navigate based on user interaction without creating a tight
 * coupling to the navigation host.
 */
class NavHostFragment : Fragment(), NavHost {
    private var mNavController: NavHostController? = null
    private var mIsPrimaryBeforeOnCreate: Boolean? = null

    // State that will be saved and restored
    private var mGraphId = 0
    private var mDefaultNavHost = false

    /**
     * Returns the [navigation controller][NavController] for this navigation host.
     * This method will return null until this host fragment's [.onCreate]
     * has been called and it has had an opportunity to restore from a previous instance state.
     *
     * @return this host's navigation controller
     * @throws IllegalStateException if called before [.onCreate]
     */
    override val navController: NavController
        get() {
            checkNotNull(mNavController) { "NavController is not available before onCreate()" }
            return mNavController!!
        }

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // This feature should probably be a first-class feature of the Fragment system,
        // but it can stay here until we can add the necessary attr resources to
        // the fragment lib.
        if (mDefaultNavHost) {
            parentFragmentManager.beginTransaction()
                .setPrimaryNavigationFragment(this)
                .commit()
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = requireContext()
        mNavController = NavHostController(context)
        mNavController!!.setLifecycleOwner(this)
        mNavController!!.setOnBackPressedDispatcher(requireActivity().onBackPressedDispatcher)
        // Set the default state - this will be updated whenever
        // onPrimaryNavigationFragmentChanged() is called
        mNavController!!.enableOnBackPressed(
            mIsPrimaryBeforeOnCreate != null && mIsPrimaryBeforeOnCreate!!
        )
        mIsPrimaryBeforeOnCreate = null
        mNavController!!.setViewModelStore(viewModelStore)
        onCreateNavController(mNavController!!)
        var navState: Bundle? = null
        if (savedInstanceState != null) {
            navState = savedInstanceState.getBundle(KEY_NAV_CONTROLLER_STATE)
            if (savedInstanceState.getBoolean(KEY_DEFAULT_NAV_HOST, false)) {
                mDefaultNavHost = true
                parentFragmentManager.beginTransaction()
                    .setPrimaryNavigationFragment(this)
                    .commit()
            }
            mGraphId = savedInstanceState.getInt(KEY_GRAPH_ID)
        }
        if (navState != null) {
            // Navigation controller state overrides arguments
            mNavController!!.restoreState(navState)
        }
        if (mGraphId != 0) {
            // Set from onInflate()
            mNavController!!.setGraph(mGraphId)
        } else {
            // See if it was set by NavHostFragment.create()
            val args = arguments
            val graphId = args?.getInt(KEY_GRAPH_ID) ?: 0
            val startDestinationArgs = args?.getBundle(KEY_START_DESTINATION_ARGS)
            if (graphId != 0) {
                mNavController!!.setGraph(graphId, startDestinationArgs)
            }
        }
    }

    /**
     * Callback for when the [NavController][.getNavController] is created. If you
     * support any custom destination types, their [Navigator] should be added here to
     * ensure it is available before the navigation graph is inflated / set.
     *
     *
     * By default, this adds a [FragmentNavigator].
     *
     *
     * This is only called once in [.onCreate] and should not be called directly by
     * subclasses.
     *
     * @param navController The newly created [NavController].
     */
    @CallSuper
    protected fun onCreateNavController(navController: NavController) {
        navController.navigatorProvider.addNavigator(
            DialogFragmentNavigator(requireContext(), childFragmentManager)
        )
        navController.navigatorProvider.addNavigator(createFragmentNavigator())
    }

    @CallSuper
    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
        if (mNavController != null) {
            mNavController!!.enableOnBackPressed(isPrimaryNavigationFragment)
        } else {
            mIsPrimaryBeforeOnCreate = isPrimaryNavigationFragment
        }
    }

    /**
     * Create the FragmentNavigator that this NavHostFragment will use. By default, this uses
     * [FragmentNavigator], which replaces the entire contents of the NavHostFragment.
     *
     *
     * This is only called once in [.onCreate] and should not be called directly by
     * subclasses.
     *
     * @return a new instance of a FragmentNavigator
     */
    @Deprecated("Use {@link #onCreateNavController(NavController)}")
    protected fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return FragmentNavigator(requireContext(), childFragmentManager, containerId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val containerView = FragmentContainerView(inflater.context)
        // When added via XML, this has no effect (since this FragmentContainerView is given the ID
        // automatically), but this ensures that the View exists as part of this Fragment's View
        // hierarchy in cases where the NavHostFragment is added programmatically as is required
        // for child fragment transactions
        containerView.id = containerId
        return containerView
    } // Fallback to using our own ID if this Fragment wasn't added via
    // add(containerViewId, Fragment)
    /**
     * We specifically can't use [View.NO_ID] as the container ID (as we use
     * [androidx.fragment.app.FragmentTransaction.add] under the hood),
     * so we need to make sure we return a valid ID when asked for the container ID.
     *
     * @return a valid ID to be used to contain child fragments
     */
    private val containerId: Int
        get() {
            val id = id
            return if (id != 0 && id != View.NO_ID) {
                id
            } else R.id.nav_host_fragment_container
            // Fallback to using our own ID if this Fragment wasn't added via
            // add(containerViewId, Fragment)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        check(view is ViewGroup) { "created host view $view is not a ViewGroup" }
        Navigation.setViewNavController(view, mNavController)
        // When added programmatically, we need to set the NavController on the parent - i.e.,
        // the View that has the ID matching this NavHostFragment.
        if (view.getParent() != null) {
            val rootView = view.getParent() as View
            if (rootView.id == id) {
                Navigation.setViewNavController(rootView, mNavController)
            }
        }
    }

    @CallSuper
    override fun onInflate(
        context: Context, attrs: AttributeSet,
        savedInstanceState: Bundle?
    ) {
        super.onInflate(context, attrs, savedInstanceState)
        val navHost = context.obtainStyledAttributes(attrs, R.styleable.NavHost)
        val graphId = navHost.getResourceId(R.styleable.NavHost_navGraph, 0)
        if (graphId != 0) {
            mGraphId = graphId
        }
        navHost.recycle()
        val a = context.obtainStyledAttributes(attrs, R.styleable.NavHostFragment)
        val defaultHost = a.getBoolean(R.styleable.NavHostFragment_defaultNavHost, false)
        if (defaultHost) {
            mDefaultNavHost = true
        }
        a.recycle()
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val navState = mNavController!!.saveState()
        if (navState != null) {
            outState.putBundle(KEY_NAV_CONTROLLER_STATE, navState)
        }
        if (mDefaultNavHost) {
            outState.putBoolean(KEY_DEFAULT_NAV_HOST, true)
        }
        if (mGraphId != 0) {
            outState.putInt(KEY_GRAPH_ID, mGraphId)
        }
    }

    companion object {
        private const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"
        private const val KEY_START_DESTINATION_ARGS = "android-support-nav:fragment:startDestinationArgs"
        private const val KEY_NAV_CONTROLLER_STATE = "android-support-nav:fragment:navControllerState"
        private const val KEY_DEFAULT_NAV_HOST = "android-support-nav:fragment:defaultHost"

        /**
         * Find a [NavController] given a local [Fragment].
         *
         *
         * This method will locate the [NavController] associated with this Fragment,
         * looking first for a [NavHostFragment] along the given Fragment's parent chain.
         * If a [NavController] is not found, this method will look for one along this
         * Fragment's [view hierarchy][Fragment.getView] as specified by
         * [Navigation.findNavController].
         *
         * @param fragment the locally scoped Fragment for navigation
         * @return the locally scoped [NavController] for navigating from this [Fragment]
         * @throws IllegalStateException if the given Fragment does not correspond with a
         * [NavHost] or is not within a NavHost.
         */
        fun findNavController(fragment: Fragment): NavController {
            var findFragment: Fragment? = fragment
            while (findFragment != null) {
                if (findFragment is NavHostFragment) {
                    return findFragment.navController
                }
                val primaryNavFragment = findFragment.parentFragmentManager
                    .primaryNavigationFragment
                if (primaryNavFragment is NavHostFragment) {
                    return primaryNavFragment.navController
                }
                findFragment = findFragment.parentFragment
            }

            // Try looking for one associated with the view instead, if applicable
            val view = fragment.view
            if (view != null) {
                return Navigation.findNavController(view)
            }
            throw IllegalStateException(
                "Fragment " + fragment
                        + " does not have a NavController set"
            )
        }

        /**
         * Create a new NavHostFragment instance with an inflated [NavGraph] resource.
         *
         * @param graphResId           resource id of the navigation graph to inflate
         * @param startDestinationArgs arguments to send to the start destination of the graph
         * @return a new NavHostFragment instance
         */
        @JvmOverloads
        fun create(
            @NavigationRes graphResId: Int,
            startDestinationArgs: Bundle? = null
        ): NavHostFragment {
            var b: Bundle? = null
            if (graphResId != 0) {
                b = Bundle()
                b.putInt(KEY_GRAPH_ID, graphResId)
            }
            if (startDestinationArgs != null) {
                if (b == null) {
                    b = Bundle()
                }
                b.putBundle(KEY_START_DESTINATION_ARGS, startDestinationArgs)
            }
            val result = NavHostFragment()
            if (b != null) {
                result.arguments = b
            }
            return result
        }
    }
}