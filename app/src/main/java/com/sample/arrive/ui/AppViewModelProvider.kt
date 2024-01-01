package com.sample.arrive.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sample.arrive.ArriveApp
import com.sample.arrive.ui.stationselect.StationSelectViewModel
import com.sample.arrive.ui.stationlist.StationListViewModel


object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            StationListViewModel(
                repository = application().container.repository,
            )
        }
        initializer {
            StationSelectViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                repository = application().container.repository
            )
        }
    }

    private fun CreationExtras.application(): ArriveApp =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ArriveApp)
}

