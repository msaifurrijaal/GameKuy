package com.saifurrijaal.gamekuy.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.saifurrijaal.gamekuy.data.database.GameDatabase
import com.saifurrijaal.gamekuy.data.database.GameFavoritDao
import com.saifurrijaal.gamekuy.data.model.GameFavoritItem
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.repository.GameFavoritRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameFavoritViewModel(application: Application) : AndroidViewModel(application) {

    private val gameFavoritRepository : GameFavoritRepository
    private val gameFavoritDao : GameFavoritDao

    private var _allGames : LiveData<List<GameFavoritItem>>
    val allGames : LiveData<List<GameFavoritItem>>
        get() = _allGames

    init {
        gameFavoritDao = GameDatabase.getInstance(application).gameFavoritDao()
        gameFavoritRepository = GameFavoritRepository(gameFavoritDao)

        _allGames = gameFavoritRepository.allGames
    }

    fun deleteGameFavorite(game: GameFavoritItem) {
        viewModelScope.launch(Dispatchers.IO) {
            gameFavoritRepository.deleteGameRepo(game)
        }
    }
}