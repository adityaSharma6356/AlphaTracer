package com.example.unspoken

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.unspoken.presentation.MainScreen
import com.example.unspoken.ui.theme.UnSpokenTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var showSplashScreen by mutableStateOf(true)
        setContent {
            UnSpokenTheme {
                val surface = MaterialTheme.colorScheme.surface
                LaunchedEffect(key1 = Unit) {
                    window.navigationBarColor = surface.toArgb()
                    delay(1500)
                    window.navigationBarColor = Color.Transparent.toArgb()
                    showSplashScreen = false
                }
                Box {
                    MainScreen()
                    if (showSplashScreen) SplashScreen()
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
    ) {
    }
}
