package com.saifurrijaal.gamekuy.ui.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GamePlatformVMFactory(private val platform: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GamePlatformViewModel(platform) as T
    }
}