package com.saifurrijaal.gamekuy.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameCategoryVMFactory(private val category: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameCategoryViewModel(category) as T
    }
}