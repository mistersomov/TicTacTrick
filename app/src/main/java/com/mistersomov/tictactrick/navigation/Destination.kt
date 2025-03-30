package com.mistersomov.tictactrick.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface Destination {
    val route: String
}

object MainMenu : Destination {
    override val route: String = "main_menu"
}

object MatchMode : Destination {
    override val route: String = "match_mode"
}

object Match : Destination {
    override val route: String = "match"
    const val boardModeArg: String = "boardMode"
    val routeWithArgs = "${route}/{${boardModeArg}}"
    val arguments = listOf(navArgument(boardModeArg) { type = NavType.IntType })
}
