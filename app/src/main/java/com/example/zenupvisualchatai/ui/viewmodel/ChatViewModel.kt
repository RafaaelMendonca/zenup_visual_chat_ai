package com.example.zenup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenup.data.repository.AuthRepository
import com.example.zenupvisualchatai.data.model.requests.ChatRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private var userId: Long = 123L

    fun setUserId(id: Long) {
        userId = id
    }

    fun sendMessage(text: String) {
        // Adiciona mensagem do usuário
        _messages.value = _messages.value + ChatMessage(text = text, isUser = true)

        // Envia para a API
        viewModelScope.launch {
            try {
                val response = repository.chat(
                    ChatRequest(id = userId, texto = text)
                )

                // Adiciona resposta do bot
                _messages.value = _messages.value + ChatMessage(
                    text = response.mensagem,
                    isUser = false
                )
            } catch (e: Exception) {
                // Adiciona mensagem de erro
                _messages.value = _messages.value + ChatMessage(
                    text = "Erro: ${e.message}",
                    isUser = false
                )
            }
        }
    }

    fun clearMessages() {
        _messages.value = emptyList()
    }
}