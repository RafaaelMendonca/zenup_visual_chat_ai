// ZenUpApi.kt
package com.example.zenupvisualchatai.data.api.endpoints

import com.example.zenupvisualchatai.data.model.requests.ChatRequest
import com.example.zenupvisualchatai.data.model.requests.LoginRequest
import com.example.zenupvisualchatai.data.model.requests.RegistroDiarioRequest
import com.example.zenupvisualchatai.data.model.responses.ChatResponse
import com.example.zenupvisualchatai.data.model.responses.LoginResponse
import com.example.zenupvisualchatai.data.model.responses.ResumoResponse
import com.example.zenupvisualchatai.data.model.responses.RegistroDiarioResponse
import retrofit2.Response
import retrofit2.http.*

interface ZenUpApi {

    @POST("api/login")
    suspend fun login(@Body req: LoginRequest): Response<LoginResponse>

    @POST("api/chat")
    suspend fun chat(@Body req: ChatRequest): Response<ChatResponse>

    @GET("api/resumo/{id_usuario}")
    suspend fun resumo(@Path("id_usuario") id: Long): Response<ResumoResponse>

    @POST("api/registro-diario")  // ← ADICIONE ESTE ENDPOINT
    suspend fun registrarCheckIn(@Body req: RegistroDiarioRequest): Response<RegistroDiarioResponse>
}