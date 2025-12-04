package com.example.zenupvisualchatai.presentation.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenupvisualchatai.data.model.requests.ChatRequest
import com.example.zenupvisualchatai.data.model.requests.LoginRequest
import com.example.zenupvisualchatai.data.network.retrofit.ApiClient
import com.example.zenupvisualchatai.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Modelo de mensagem para exibir no chat
data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

class ChatViewModel : ViewModel() {

    private val TAG = "ChatViewModel"

    // ID FIXO DO USUÁRIO (exigência do cliente)
    private val userId = 1234L

    // Lista de mensagens do chat
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    // AGORA PEGA DO BUILD CONFIG
    private val apiKey = BuildConfig.API_KEY_CHATBOT

    init {
        Log.e(TAG, "ViewModel criado — iniciando login automático")
        login()
    }

    private fun login() {
        viewModelScope.launch {
            try {
                Log.e(TAG, "Enviando login…")

                val resp = ApiClient.api.login(LoginRequest(apiKey))

                if (!resp.isSuccessful) {
                    Log.e(TAG, "LOGIN FALHOU: código=${resp.code()}")
                    return@launch
                }

                ApiClient.authToken = resp.body()?.token
                Log.e(TAG, "LOGIN OK — token recebido: ${ApiClient.authToken}")

            } catch (e: Exception) {
                Log.e(TAG, "EXCEÇÃO NO LOGIN: ${e.message}", e)
            }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        // Exibir mensagem do usuário instantaneamente
        _messages.value = _messages.value + ChatMessage(text, true)

        viewModelScope.launch {
            try {
                _loading.value = true

                val resp = ApiClient.api.chat(ChatRequest(userId, text))

                if (!resp.isSuccessful) {
                    _messages.value += ChatMessage("Erro: ${resp.code()}", false)
                    return@launch
                }

                val resposta = resp.body()?.mensagem ?: "(sem resposta)"

                // Exibir resposta da IA
                _messages.value += ChatMessage(resposta, false)

            } catch (e: Exception) {
                _messages.value += ChatMessage("Erro: ${e.message}", false)
            } finally {
                _loading.value = false
            }
        }
    }
}
