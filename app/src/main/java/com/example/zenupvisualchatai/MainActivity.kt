package com.example.zenupvisualchatai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.zenupvisualchatai.presentation.chat.ChatScreen
import com.example.zenupvisualchatai.presentation.chat.ChatViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ChatViewModel() // apenas 1 VM para toda a tela

        setContent {
            MaterialTheme {
                ChatScreen(viewModel = viewModel)
            }
        }
    }
}
