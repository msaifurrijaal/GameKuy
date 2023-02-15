package com.saifurrijaal.gamekuy.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saifurrijaal.gamekuy.data.model.GameResponseItem

@Dao
interface GameFavoritDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertGame(games: GameResponseItem)

    @Delete
    suspend fun deleteGame(games: GameResponseItem)

    @Query("SELECT * FROM gameItem")
    fun getGames() : LiveData<List<GameResponseItem>>

    @Query("SELECT * FROM gameItem WHERE id=:game_id")
    fun getGame(game_id: Int) : LiveData<List<GameResponseItem>>

}