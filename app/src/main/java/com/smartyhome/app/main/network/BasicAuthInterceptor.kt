package com.rmindr.app.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(var user: String, var password: String) : Interceptor {

    private var credentials: String? = null


    override fun intercept(chain: Interceptor.Chain): Response {
        this.credentials = Credentials.basic(user, password)
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }

}