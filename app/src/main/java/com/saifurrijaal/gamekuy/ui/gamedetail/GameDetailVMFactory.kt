package com.saifurrijaal.gamekuy.ui.gamedetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameDetailVMFactory(private val id: Int, private val application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameDetailViewModel(id, application) as T
    }
}