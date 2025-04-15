package com.bornahoosh.afraagpstracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bornahoosh.afraagpstracker.presentation.LoginScreen
import com.bornahoosh.afraagpstracker.presentation.main.MainScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MAIN) {
            MainScreen(rootNavController = navController) // HomeScreen دیگه اینجا نیست، MainScreen نقش صفحه‌ی مادر رو داره
        }

        // مسیرهای داخلی (home, history, operations, devices) در MainScreen مدیریت می‌شن
    }
}
