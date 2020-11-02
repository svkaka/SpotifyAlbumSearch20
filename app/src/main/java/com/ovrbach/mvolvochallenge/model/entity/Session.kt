package com.ovrbach.mvolvochallenge.model.entity


interface Session {
    fun attemptedLogin(): Boolean
    fun token(): String?
    fun saveToken(token: String)
    fun cleanSession(): Boolean
}
