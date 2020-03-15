package ru.nikitae57.viewmodel.graph

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GraphViewModelFactory(val app: Application)
    : ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == GraphViewModel::class.java) {
            GraphViewModel(app) as T
        } else {
            super.create(modelClass)
        }
    }
}