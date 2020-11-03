package com.ovrbach.mvolvochallenge.data

import android.app.Application
import android.content.res.Resources
import com.ovrbach.mvolvochallenge.R
import java.lang.IllegalStateException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandler @Inject constructor(resources: Resources) {

    fun mapError(throwable: Throwable) =
            when (throwable) {
                is UnknownHostException -> noInternetConnection
                else -> throwable
            }


    private val noInternetConnection = KnownError(resources.getString(R.string.error_no_internet))

    data class KnownError(
            override val message: String
    ) : IllegalStateException(message)

}