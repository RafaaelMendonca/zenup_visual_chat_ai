package com.example.zenupvisualchatai.data.network.okHttp

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

class AuthInterceptor(
    private val tokenProvider: () -> String?
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = tokenProvider()

        val builder = original.newBuilder()
            .header("Accept", "application/json")

        if (!token.isNullOrBlank()) {
            builder.header("Authorization", "Bearer $token")
        }

        val request = builder.build()

        // Log simples (o ideal é deixar só o LoggingInterceptor do OkHttp no Android)
        val response = chain.proceed(request)
        return response
    }
}
