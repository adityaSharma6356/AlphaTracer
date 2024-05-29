package com.example.unspoken.presentation

import android.view.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unspoken.presentation.home.HomeUi
import com.example.unspoken.presentation.login.LoginUi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    window: Window,
    viewModel: MainViewModel = hiltViewModel()
) {
    val mainNavController = rememberNavController()
    val startDestination = remember {
        derivedStateOf {
            if (viewModel.isLoggedIn.value) {
                "home"
            } else {
                "login"
            }
        }
    }
    val color = MaterialTheme.colorScheme.background.toArgb()
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground),
        navController = mainNavController,
//        startDestination = startDestination.value,
        startDestination = "login"
    ) {
        composable("login") {
            val darkTheme = isSystemInDarkTheme()
            LaunchedEffect(Unit) {
//                window.navigationBarColor = color
                if(!darkTheme) window.statusBarColor = Color(1,1,1, 60).toArgb()
            }
            LoginUi(mainNavController)
        }
        composable("home") {
//            LaunchedEffect(Unit) {
//                window.navigationBarColor = color
//            }
            HomeUi()
        }
    }
}
