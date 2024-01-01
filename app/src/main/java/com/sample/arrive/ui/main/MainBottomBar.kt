package com.sample.arrive.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


private data class BottomBarIcon(val screen: Screen, val icon: ImageVector)

private val items = listOf(
    BottomBarIcon(Screen.List, Icons.TwoTone.List),
    BottomBarIcon(Screen.Add,  Icons.TwoTone.Add ),
    BottomBarIcon(Screen.Info, Icons.TwoTone.Info)
)

@Composable
fun MainBottomBar(
    screen: Screen,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        items.forEach { item ->
            val selected = screen.route == item.screen.route
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                label = { Text(text = stringResource(id = item.screen.titleId)) },
                selected = selected,
                enabled = !selected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true      //同じ画面の場合は更新しない
                        restoreState = true
                    }
                }
            )
        }
    }
}