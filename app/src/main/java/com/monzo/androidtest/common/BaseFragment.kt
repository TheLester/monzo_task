package com.monzo.androidtest.common

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.monzo.androidtest.R

abstract class BaseFragment : Fragment() {

    fun handleError(throwable: Throwable) {
        view?.let {
            Snackbar.make(it, R.string.error_generic, Snackbar.LENGTH_LONG).show()
        }
    }
}
