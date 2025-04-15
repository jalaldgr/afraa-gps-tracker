package com.bornahoosh.afraagpstracker.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
