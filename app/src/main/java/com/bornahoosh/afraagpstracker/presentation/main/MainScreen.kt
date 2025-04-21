package com.bornahoosh.afraagpstracker.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bornahoosh.afraagpstracker.presentation.components.AppTopBar
import com.bornahoosh.afraagpstracker.presentation.components.BottomNavBar
import com.bornahoosh.afraagpstracker.presentation.devices.DevicesScreen
import com.bornahoosh.afraagpstracker.presentation.history.HistoryScreen
import com.bornahoosh.afraagpstracker.presentation.home.MapScreen
import com.bornahoosh.afraagpstracker.presentation.operations.OperationsScreen

@Composable
fun MainScreen(
    rootNavController: NavHostController
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    Scaffold(
        topBar = {
            AppTopBar(
                title = when (currentRoute) {
                    "home" -> "خانه"
                    "history" -> "تاریخچه"
                    "operations" -> "عملیات"
                    "devices" -> "دستگاه‌ها"
                    else -> "Afraa GPS"
                }
                // می‌تونی اینجا دکمه لاگ‌اوت یا تنظیمات رو اضافه کنی
                // onSettingsClick = { rootNavController.navigate("login") }
            )
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") { MapScreen() }
                composable("history") { HistoryScreen() }
                composable("operations") { OperationsScreen() }
                composable("devices") { DevicesScreen() }
            }
        }
    }
}
