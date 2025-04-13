
// HomeScreen.kt
package com.bornahoosh.afraagpstracker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bornahoosh.afraagpstracker.LoginViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel()  // استفاده از viewModel بدون فکتوری

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("خوش آمدید!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            viewModel.logout()
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text("خروج")
        }
    }
}
