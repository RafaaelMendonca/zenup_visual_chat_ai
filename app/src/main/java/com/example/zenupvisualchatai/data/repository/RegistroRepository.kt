package com.example.zenupvisualchatai.data.repository

import com.example.zenupvisualchatai.data.api.endpoints.ZenUpApi
import com.example.zenupvisualchatai.data.network.retrofit.ApiClient
import com.example.zenupvisualchatai.data.model.requests.RegistroDiarioRequest
import com.example.zenupvisualchatai.data.model.responses.RegistroDiarioResponse
import java.io.IOException

class RegistroRepository(
    private val apiService: ZenUpApi = ApiClient.api
) {

    suspend fun registrarCheckIn(request: RegistroDiarioRequest): RegistroDiarioResponse {
        val response = apiService.registrarCheckIn(request)

        if (response.isSuccessful) {
            return response.body()
                ?: throw IOException("Resposta de registro vazia.")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Erro desconhecido"
            throw IOException("Erro ao registrar check-in. HTTP ${response.code()}: $errorBody")
        }
    }
}
