package com.ovrbach.mvolvochallenge.remote

import com.ovrbach.mvolvochallenge.app.di.qualifier.ClientId
import com.ovrbach.mvolvochallenge.app.di.qualifier.ClientSecret
import com.ovrbach.mvolvochallenge.model.dto.response.TokenResponse
import com.ovrbach.mvolvochallenge.model.entity.Session
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
        @ClientId private val client: String,
        @ClientSecret private val secret: String,
        private val session: Session,
        private val authService: AuthService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? = runBlocking {
        val activeToken = if (session.token() == null) {
            session.saveToken(renewToken().access_token)
            session.token()
        } else {
            session.token()
        } ?: return@runBlocking null

        val request = chain.request().newBuilder().apply {
            header("Authorization", activeToken)
            header("Content-Type", "application/x-www-form-urlencoded")
        }.build()

        chain.proceed(request)
    }


    private suspend fun renewToken(): TokenResponse {
        return authService.authenticate(
            Credentials.basic(client, secret)
        )
    }
}