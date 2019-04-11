package com.monzo.androidtest.common

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView

abstract class BaseEpoxyFragment : BaseFragment() {

    protected val epoxyController by lazy { epoxyController() }

    /**
     * Provide the EpoxyController to use when building models for this Fragment.
     * Basic usages can simply use [simpleController]
     */
    abstract fun epoxyController(): EpoxyController

    abstract fun epoxyRecyclerView(): EpoxyRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        epoxyRecyclerView().setController(epoxyController)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }

}


/**
 * This builds Epoxy models in a background thread.
 */
open class ItemsEpoxyController(
        val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {

    override fun buildModels() {
        buildModelsCallback()
    }
}


/**
 * Create a [MvRxEpoxyController] that builds models with the given callback.
 */
fun BaseEpoxyFragment.simpleController(
        buildModels: EpoxyController.() -> Unit
) = ItemsEpoxyController {
    // Models are built asynchronously, so it is possible that this is called after the fragment
    // is detached under certain race conditions.
    if (view == null || isRemoving) return@ItemsEpoxyController
    buildModels()
}
