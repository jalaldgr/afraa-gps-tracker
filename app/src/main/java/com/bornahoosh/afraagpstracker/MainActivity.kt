package com.bornahoosh.afraagpstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bornahoosh.afraagpstracker.presentation.LoginScreen
import com.bornahoosh.afraagpstracker.presentation.HomeScreen
import com.bornahoosh.afraagpstracker.presentation.theme.AfraaGPSTrackerTheme

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

