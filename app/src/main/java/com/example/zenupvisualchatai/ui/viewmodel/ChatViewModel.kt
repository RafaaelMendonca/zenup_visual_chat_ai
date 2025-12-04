// ChatViewModel.kt
package com.example.zenup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenupvisualchatai.data.repository.AuthRepository
import com.example.zenupvisualchatai.data.model.requests.ChatRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun enviarMensagem(texto: String, idUsuario: Long) {
        if (texto.isBlank()) return

        // Adiciona mensagem do usuário
        val userMessage = ChatMessage(text = texto, isUser = true)
        _messages.value = _messages.value + userMessage

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val request = ChatRequest(
                    mensagem = texto,
                    id_usuario = idUsuario
                )

                val response = repository.chat(request)

                // Adiciona resposta do bot
                val botMessage = ChatMessage(
                    text = response.mensagem,
                    isUser = false
                )
                _messages.value = _messages.value + botMessage

            } catch (e: Exception) {
                _error.value = e.message ?: "Erro ao enviar mensagem"

                // Remove a mensagem do usuário em caso de erro (opcional)
                // _messages.value = _messages.value.dropLast(1)

            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limparErro() {
        _error.value = null
    }

    fun limparChat() {
        _messages.value = emptyList()
    }
}