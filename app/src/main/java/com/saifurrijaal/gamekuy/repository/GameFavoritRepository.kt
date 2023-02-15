package com.saifurrijaal.gamekuy.repository

import androidx.lifecycle.LiveData
import com.saifurrijaal.gamekuy.data.database.GameFavoritDao
import com.saifurrijaal.gamekuy.data.model.GameFavoritItem
import com.saifurrijaal.gamekuy.data.model.GameResponseItem

class GameFavoritRepository(private val gameFavoritDao: GameFavoritDao) {

    val allGames = gameFavoritDao.getGames()

    fun getGameItem(gameId: Int) : LiveData<List<GameFavoritItem>> {
        var game = gameFavoritDao.getGame(gameId)
        return game
    }

    suspend fun upsertGamesRepo(game: GameFavoritItem) {
        gameFavoritDao.upsertGame(game)
    }

    suspend fun deleteGameRepo(game: GameFavoritItem) {
        gameFavoritDao.deleteGame(game)
    }
}