package com.mistersomov.tictactrick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mistersomov.core.ui_kit.TicTacTrickTheme
import com.mistersomov.tictactrick.presentation.screen.match.MatchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicTacTrickActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacTrickTheme {
                MatchScreen()
            }
        }
    }
}