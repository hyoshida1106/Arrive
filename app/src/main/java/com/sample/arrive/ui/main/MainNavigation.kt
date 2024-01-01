package com.sample.arrive.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sample.arrive.ui.stationselect.StationEditScreen
import com.sample.arrive.R
import com.sample.arrive.ui.information.InformationScreen
import com.sample.arrive.ui.stationlist.StationListScreen
import com.sample.arrive.ui.stationselect.StationAddScreen


sealed class Screen(
    val route: String,
    val titleId: Int
) {
    data object List: Screen("List", R.string.screen_station_list_title)
    data object Add : Screen("Add",  R.string.screen_add_station_title)
    data object Edit: Screen("Edit", R.string.screen_edit_station_title)
    data object Info: Screen("Info", R.string.screen_info_title)
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(
            route = Screen.List.route
        ) {
            StationListScreen(navController = navController)
        }
        composable(
            route = Screen.Add.route
        ) {
            StationAddScreen(navController = navController)
        }
        composable(
            route = "${Screen.Edit.route}/{StationId}",
            arguments = listOf(navArgument(name = "StationId") { type = NavType.LongType })
        ) {
            StationEditScreen(navController = navController)
        }
        composable(
            route = Screen.Info.route
        ) {
            InformationScreen(navController = navController)
        }
    }
}

