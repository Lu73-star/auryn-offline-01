package com.auryn.offline.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.auryn.offline.presentation.chat.ChatScreen
import com.auryn.offline.presentation.theme.AurynOfflineTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the Auryn Offline application
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AurynOfflineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen(
                        onNavigateToSettings = {
                            // Navigation to settings will be implemented in future
                        }
                    )
                }
            }
        }
    }
}
