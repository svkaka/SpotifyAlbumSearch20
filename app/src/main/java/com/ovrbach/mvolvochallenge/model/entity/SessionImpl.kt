package com.ovrbach.mvolvochallenge.model.entity

import java.util.concurrent.atomic.AtomicBoolean

class SessionImpl: Session {
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