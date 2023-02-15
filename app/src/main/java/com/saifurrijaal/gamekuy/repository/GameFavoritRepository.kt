package com.saifurrijaal.gamekuy.repository

import androidx.lifecycle.LiveData
import com.saifurrijaal.gamekuy.data.database.GameFavoritDao
import com.saifurrijaal.gamekuy.data.model.GameResponseItem

class GameFavoritRepository(private val gameFavoritDao: GameFavoritDao) {

    val allGames: LiveData<List<GameResponseItem>> = gameFavoritDao.getGames()

    suspend fun upsertGamesRepo(game: GameResponseItem) {
        gameFavoritDao.upsertGame(game)
    }

    suspend fun deleteGameRepo(game: GameResponseItem) {
        gameFavoritDao.deleteGame(game)
    }
}