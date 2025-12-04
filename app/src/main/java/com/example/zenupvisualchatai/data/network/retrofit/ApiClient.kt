package com.example.zenupvisualchatai.data.network.retrofit

import com.example.zenupvisualchatai.data.api.endpoints.ZenUpApi
import com.example.zenupvisualchatai.data.network.okHttp.provideOkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "http://192.168.0.2:8000/"

    // Token que será preenchido após o login
    var authToken: String? = null

    private val client by lazy {
        provideOkHttpClient { authToken }
    }

    val api: ZenUpApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ZenUpApi::class.java)
    }
}
