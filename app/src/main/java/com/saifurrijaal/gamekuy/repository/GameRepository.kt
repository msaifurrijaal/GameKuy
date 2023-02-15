package com.saifurrijaal.gamekuy.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.saifurrijaal.gamekuy.data.database.GameDao
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameRepository(private val gameDao: GameDao ) {

    private lateinit var gamesData: List<GameResponseItem>

    val allGames = gameDao.getGames()

    fun getGameItem(gameId: Int) : LiveData<List<GameResponseItem>> {
        var game = gameDao.getGame(gameId)
        return game
    }

    suspend fun refreshGame() {
        withContext(Dispatchers.IO) {
            val games = RetrofitInstance.getApiService().getListGames()
            gameDao.upsertMeal(games)
        }
    }
}