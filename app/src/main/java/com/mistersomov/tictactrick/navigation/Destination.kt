package com.mistersomov.tictactrick.navigation

sealed interface Destination {
    val route: String
}

object MainMenu : Destination {
    override val route: String = "main_menu"
}

object Match : Destination {
    override val route: String = "match"
}
