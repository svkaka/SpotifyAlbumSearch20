package com.ovrbach.mvolvochallenge.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.atomic.AtomicBoolean

val logger = HttpLoggingInterceptor().also {
    it.level = HttpLoggingInterceptor.Level.HEADERS
}

val authClient = OkHttpClient.Builder()
    .addInterceptor(logger).build()

val authRetrofit = Retrofit.Builder()
    .baseUrl(RemoteServiceConstants.BASE_AUTH_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .client(authClient).build()

val authService = authRetrofit.create(AuthService::class.java)

interface Session {
    fun attemptedLogin(): Boolean
    fun token(): String?
    fun saveToken(token: String)
    fun cleanSession(): Boolean
}

val session = object : Session {
    @Volatile
    var token: String? = null

    var attemptedLogin = AtomicBoolean(false)

    @Synchronized
    override fun attemptedLogin(): Boolean = attemptedLogin.get()

    @Synchronized
    override fun token(): String? = token?.let { token -> "Bearer $token" }

    @Synchronized
    override fun saveToken(token: String) {
        this.token = token
    }

    @Synchronized
    override fun cleanSession(): Boolean {
        token = null
        return true
    }

}

val apiClient = OkHttpClient.Builder()
    .addInterceptor(
        AuthInterceptor(
            session = session,
            client = RemoteServiceConstants.CLIENT_CODE,
            secret = RemoteServiceConstants.SECRET,
            authService = authService
        )
    )
    .addInterceptor(logger)
    .build()

val apiRetrofit = Retrofit.Builder()
    .baseUrl(RemoteServiceConstants.BASE_API_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .client(apiClient)
    .build()

