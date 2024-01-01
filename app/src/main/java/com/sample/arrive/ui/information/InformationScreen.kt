package com.sample.arrive.ui.information

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.sample.arrive.ui.main.MainScreen
import com.sample.arrive.ui.main.Screen

@Composable
fun InformationScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    MainScreen(
        screen = Screen.Info,
        navController = navController
    ) {
        Text(
            text = "InformationScreen",
            modifier = modifier
        )
    }
}
