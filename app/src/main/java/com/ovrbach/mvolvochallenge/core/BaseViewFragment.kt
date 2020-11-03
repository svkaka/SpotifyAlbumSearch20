package com.ovrbach.mvolvochallenge.core

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseViewFragment<B : ViewBinding>(layoutRes: Int) : Fragment(layoutRes) {

    private var snackBar: Snackbar? = null
    var binding: B? = null

    abstract fun bindView(view: View): B

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = bindView(view).also {
            it.onViewCreated()
        }
    }

    abstract fun B.onViewCreated()

    override fun onDestroyView() {
        super.onDestroyView()
        dismissSnackbar()
        binding = null
    }

    protected fun buildErrorSnackbar(
        message: CharSequence,
        action: ()-> Unit
    ): Snackbar {
        dismissSnackbar()

        return Snackbar.make(
            binding!!.root, message,
            Snackbar.LENGTH_INDEFINITE
        ).also { snack ->
            snack.setAction("Retry") {
                action()
            }
            snackBar = snack
        }
    }

    protected fun dismissSnackbar() {
        snackBar?.dismiss()
        snackBar = null
    }
}