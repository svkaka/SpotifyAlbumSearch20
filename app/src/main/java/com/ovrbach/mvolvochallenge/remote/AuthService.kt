package com.ovrbach.mvolvochallenge.remote

import com.ovrbach.mvolvochallenge.model.dto.auth.TokenResponse
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun authenticate(
        @Header("Authorization") credentials: String,
        @Query("grant_type") type: String = "client_credentials"
    ): TokenResponse
}