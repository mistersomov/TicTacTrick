package com.mistersomov.tictactrick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.mistersomov.core.ui_kit.TicTacTrickTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicTacTrickActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacTrickTheme {
                Scaffold { paddingValue ->
                    TicTacTrickNavGraph(modifier = Modifier.padding(paddingValue))
                }
            }
        }
    }
}