package com.example.zenup.data.repository

import com.example.zenupvisualchatai.data.network.retrofit.ApiClient
import com.example.zenupvisualchatai.data.api.endpoints.ZenUpApi

import com.example.zenupvisualchatai.data.model.requests.LoginRequest
import com.example.zenupvisualchatai.data.model.requests.ChatRequest

import com.example.zenupvisualchatai.data.model.responses.LoginResponse
import com.example.zenupvisualchatai.data.model.responses.ChatResponse
import com.example.zenupvisualchatai.data.model.responses.ResumoResponse

import java.io.IOException

class AuthRepository(
    private val apiService: ZenUpApi = ApiClient.api
) {

    suspend fun login(request: LoginRequest): LoginResponse {
        val response = apiService.login(request)

        if (response.isSuccessful) {
            val loginResponse = response.body()
                ?: throw IOException("Resposta de login vazia.")

            ApiClient.authToken = loginResponse.token
            return loginResponse
        } else {
            val errorBody = response.errorBody()?.string() ?: "Erro desconhecido"
            throw IOException("Falha no login. HTTP ${response.code()}: $errorBody")
        }
    }

    suspend fun chat(request: ChatRequest): ChatResponse {
        val response = apiService.chat(request)

        if (response.isSuccessful) {
            return response.body()
                ?: throw IOException("Resposta de chat vazia.")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Erro desconhecido"
            throw IOException("Erro no chat. HTTP ${response.code()}: $errorBody")
        }
    }

    suspend fun resumo(idUsuario: Long): ResumoResponse {
        val response = apiService.resumo(idUsuario)

        if (response.isSuccessful) {
            return response.body()
                ?: throw IOException("Resposta de resumo vazia.")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Erro desconhecido"
            throw IOException("Erro ao buscar resumo. HTTP ${response.code()}: $errorBody")
        }
    }
}
