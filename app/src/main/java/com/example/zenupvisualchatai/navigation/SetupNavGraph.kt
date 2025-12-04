package com.example.zenupvisualchatai.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zenup.ui.screen.Chat
import com.example.zenup.ui.screen.Login
import com.example.zenup.ui.screen.TelaInicial
import com.example.zenup.ui.screen.Home
import com.example.zenup.ui.screen.Energia
import com.example.zenup.ui.screen.Estresse
import com.example.zenup.ui.screen.Humor
import com.example.zenup.ui.screen.IniciarChat

// Defina as rotas como constantes para evitar erros de digitação
object Route {
    const val TELA_INICIAL = "tela_inicial"
    const val LOGIN = "login"

    const val HOME = "home"

    const val ENERGIA = "energia"
    const val ESTRESSE = "estresse"
    const val HUMOR = "humor"

    const val INICIARCHAT = "iniciarchat"

    const val CHAT = "chat"
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.TELA_INICIAL
    ) {
        composable(route = Route.TELA_INICIAL) {
            TelaInicial(navController = navController)
        }
        composable(route = Route.LOGIN) {
            Login(navController = navController)
        }
        composable(route = Route.HOME) {
            Home(navController = navController)
        }

        composable(route = Route.ENERGIA) {
            Energia(navController = navController)
        }

        composable(route = Route.ESTRESSE) {
            Estresse(navController = navController)
        }

        composable(route = Route.HUMOR) {
            Humor(navController = navController)
        }

        composable (route = Route.INICIARCHAT){
            IniciarChat(navController = navController)
        }
        composable (route = Route.CHAT){
            Chat(navController = navController)
        }
    }
}
