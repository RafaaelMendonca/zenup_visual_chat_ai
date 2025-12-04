package com.example.zenupvisualchatai.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onExit: () -> Unit = {}
) {
    var message by remember { mutableStateOf("") }
    val messages by viewModel.messages.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // HEADER GRADIENTE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFD8C7FF),
                            Color(0xFFFFE3D3)
                        )
                    )
                )
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 18.dp, start = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "voltar",
                    tint = Color(0xFF4A2C8A)
                )
            }

            Button(
                onClick = onExit,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A2C8A)
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 15.dp, end = 16.dp)
                    .height(36.dp)
            ) {
                Text("Sair", color = Color.White)
            }
        }

        // TEXTO INICIAL (SOME APÓS A 1ª MENSAGEM)
        if (messages.isEmpty()) {
            Spacer(Modifier.height(20.dp))
            Text(
                "Como posso\nte ajudar?",
                color = Color(0xFF4A2C8A),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // LISTA DE MENSAGENS
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            items(messages) { msg ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (msg.isUser) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 20.dp,
                                    topEnd = 20.dp,
                                    bottomEnd = if (!msg.isUser) 20.dp else 4.dp,
                                    bottomStart = if (msg.isUser) 20.dp else 4.dp
                                )
                            )
                            .background(
                                if (msg.isUser) Color(0xFFB9A7FF)
                                else Color(0xFFEDEDED)
                            )
                            .padding(14.dp)
                    ) {
                        Text(msg.text, color = Color.Black)
                    }
                }
            }
        }

        // CAMPO DE DIGITAÇÃO (SEM EMOJI)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(60.dp)
        ) {

            TextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text("Digite sua mensagem...") },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F1F1),
                    unfocusedContainerColor = Color(0xFFF1F1F1),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(Modifier.width(10.dp))

            IconButton(
                onClick = {
                    viewModel.sendMessage(message)
                    message = ""
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4A2C8A))
            ) {
                Icon(Icons.Default.Send, contentDescription = "Enviar", tint = Color.White)
            }
        }
    }
}
