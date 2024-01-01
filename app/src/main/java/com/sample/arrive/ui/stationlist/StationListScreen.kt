package com.sample.arrive.ui.stationlist

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sample.arrive.ui.main.MainScreen
import com.sample.arrive.ui.AppViewModelProvider
import com.sample.arrive.ui.main.Screen

@Composable
fun StationListScreen(
    navController: NavHostController
) {
    val model: StationListViewModel = viewModel(factory = AppViewModelProvider.factory)

    MainScreen(
        screen = Screen.List,
        navController = navController
    ) {
        StationListView(
            model = model,
            onItemEdit = { stationInfo ->
                navController.navigate(
                    route = "${Screen.Edit.route}/${stationInfo.stationId}"
                )
            },
            onItemUpdate = { stationInfo ->
                model.update(stationInfo)
            },
            onItemRemove = { stationInfo ->
                model.remove(stationInfo)
            }
        )
    }
}
