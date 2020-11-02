package com.ovrbach.mvolvochallenge.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseViewFragment<B : ViewBinding>(layoutRes: Int) : Fragment(layoutRes) {

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
        binding = null
    }
}