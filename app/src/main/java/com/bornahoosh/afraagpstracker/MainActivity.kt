package com.bornahoosh.afraagpstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bornahoosh.afraagpstracker.screens.LoginScreen
import com.bornahoosh.afraagpstracker.screens.HomeScreen
import com.bornahoosh.afraagpstracker.ui.theme.AfraaGPSTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AfraaGPSTrackerTheme {
                // کنترلر ناوبری برای هدایت بین صفحه‌ها
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    // صفحه لاگین
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                // در صورت ورود موفق، به صفحه خانه می‌رود
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                    // صفحه خانه
                    composable("home") {
                        HomeScreen(navController = navController)
                    }
                }
            }
        }
    }
}

