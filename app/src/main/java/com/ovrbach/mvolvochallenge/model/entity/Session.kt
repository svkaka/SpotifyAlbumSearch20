package com.ovrbach.mvolvochallenge.model.entity

interface Session {
    val isLoggedIn: Boolean

    fun saveToken(token: String?)
    val token: String?

    fun invalidate()
}