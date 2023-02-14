package com.saifurrijaal.gamekuy.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saifurrijaal.gamekuy.data.database.GameDao
import com.saifurrijaal.gamekuy.data.database.GameDatabase
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository : GameRepository
    private val gameDao : GameDao

    private var _allGames : LiveData<List<GameResponseItem>>
    val allGames : LiveData<List<GameResponseItem>>
        get() = _allGames

    private var _games = MutableLiveData<GameResponseItem>()
    val games : LiveData<GameResponseItem>
        get() = _games

    companion object{
        private const val TAG = "HomeViewModel"
    }

    init {
        gameDao = GameDatabase.getInstance(application).gameDao()
        gameRepository = GameRepository(gameDao)

        viewModelScope.launch {
            try {
                gameRepository.refreshGame()
            } catch (t: Throwable) {
                Log.e(TAG, "error : ${t.toString()}")
            }
        }
        _allGames = gameRepository.games
    }

}