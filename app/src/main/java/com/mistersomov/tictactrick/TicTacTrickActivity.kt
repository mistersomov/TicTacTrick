package com.mistersomov.tictactrick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mistersomov.tictactrick.presentation.screen.GameScreen
import com.mistersomov.tictactrick.ui.theme.HappyAndHealthTheme

class TicTacTrickActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HappyAndHealthTheme {
                GameScreen()
            }
        }
    }
}