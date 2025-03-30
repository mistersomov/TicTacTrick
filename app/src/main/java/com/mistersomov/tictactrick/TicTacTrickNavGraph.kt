package com.mistersomov.tictactrick

import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.navigation.MainMenu
import com.mistersomov.tictactrick.navigation.Match
import com.mistersomov.tictactrick.navigation.MatchMode
import com.mistersomov.tictactrick.presentation.screen.main_menu.MainMenuScreen
import com.mistersomov.tictactrick.presentation.screen.match.MatchScreen
import com.mistersomov.tictactrick.presentation.screen.match_mode.MatchModeScreen

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
                onPlayClick = { navController.navigate(MatchMode.route) },
            )
        }
        composable(
            route = MatchMode.route,
            enterTransition = { scaleIn() },
            exitTransition = { scaleOut() },
        ) {
            MatchModeScreen(
                onClick = { mode -> navController.navigate("${Match.route}/$mode") },
            )
        }
        composable(
            route = Match.routeWithArgs,
            arguments = Match.arguments,
            enterTransition = { scaleIn() },
            exitTransition = { fadeOut() },
        ) { navBackStackEntry ->
            val boardMode = BoardMode.entries.find {
                it.value == navBackStackEntry.arguments?.getInt(Match.boardModeArg)
            } ?: BoardMode.FOUR

            MatchScreen(boardMode = boardMode)
        }
    }
}