package com.ovrbach.mvolvochallenge.model.dto.auth

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: String
)