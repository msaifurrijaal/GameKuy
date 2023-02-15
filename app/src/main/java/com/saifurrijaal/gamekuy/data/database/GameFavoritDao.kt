package com.saifurrijaal.gamekuy.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saifurrijaal.gamekuy.data.model.GameFavoritItem
import com.saifurrijaal.gamekuy.data.model.GameResponseItem

@Dao
interface GameFavoritDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertGame(games: GameFavoritItem)

    @Delete
    suspend fun deleteGame(games: GameFavoritItem)

    @Query("SELECT * FROM gameFavoritItem")
    fun getGames() : LiveData<List<GameFavoritItem>>

    @Query("SELECT * FROM gameFavoritItem WHERE id=:game_id")
    fun getGame(game_id: Int) : LiveData<List<GameFavoritItem>>

}