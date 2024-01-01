package com.sample.arrive.ui.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MainScreen(
    screen: Screen,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopBar(
                screen = screen,
                actions = actions
            )
        },
        bottomBar = {
            MainBottomBar(
                screen = screen,
                navController = navController
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.secondary,
            content = { content() }
        )
    }
}

