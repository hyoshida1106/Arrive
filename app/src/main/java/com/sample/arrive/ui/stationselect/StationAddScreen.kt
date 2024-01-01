package com.sample.arrive.ui.stationselect

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sample.arrive.R
import com.sample.arrive.ui.AppViewModelProvider
import com.sample.arrive.ui.main.MainScreen
import com.sample.arrive.ui.main.Screen

@Composable
fun StationAddActions( ) {
    val model: StationSelectViewModel = viewModel(factory = AppViewModelProvider.factory)
    IconButton(
        onClick = { model.action = StationSelectViewModel.Action.COMMIT },
        enabled = model.selectedStation != null
    ) {
        Icon(painterResource(R.drawable.outline_add_circle_outline_24), "追加")
    }
    IconButton(
        onClick = { model.action = StationSelectViewModel.Action.CANCEL }
    ) {
        Icon(painterResource(R.drawable.outline_cancel_24), "キャンセル")
    }
}

@Composable
fun StationAddScreen(
    navController: NavHostController
) {
    val model: StationSelectViewModel = viewModel(factory = AppViewModelProvider.factory)

    MainScreen(
        screen = Screen.Add,
        navController = navController,
        actions = { StationAddActions() }
    ) {
        StationSelectView(
            model = model
        )

        if(model.action != StationSelectViewModel.Action.NONE) {
            if(model.action == StationSelectViewModel.Action.COMMIT) {
                model.commit()
            }
            navController.navigate(Screen.List.route) {
                popUpTo(Screen.List.route)
            }
            model.action = StationSelectViewModel.Action.NONE
        }
    }
}
