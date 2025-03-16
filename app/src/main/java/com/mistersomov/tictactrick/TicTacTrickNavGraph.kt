package com.mistersomov.tictactrick

import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mistersomov.tictactrick.navigation.MainMenu
import com.mistersomov.tictactrick.navigation.Match
import com.mistersomov.tictactrick.presentation.screen.main_menu.MainMenuScreen
import com.mistersomov.tictactrick.presentation.screen.match.MatchScreen

@Composable
fun TicTacTrickNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainMenu.route,
    ) {
        composable(route = MainMenu.route) {
            MainMenuScreen(
                onPlayClick = { navController.navigate(Match.route) },
            )
        }
        composable(
            route = Match.route,
            enterTransition = { scaleIn() },
            exitTransition = { fadeOut() },
        ) {
            MatchScreen()
        }
    }
}