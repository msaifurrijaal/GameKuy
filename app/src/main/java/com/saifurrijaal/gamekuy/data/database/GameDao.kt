package com.saifurrijaal.gamekuy.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saifurrijaal.gamekuy.data.model.GameResponseItem

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(games: List<GameResponseItem>)

    @Query("SELECT * FROM gameItem")
    fun getGames() : LiveData<List<GameResponseItem>>

    @Query("SELECT * FROM gameItem WHERE id=:game_id")
    fun getGame(game_id: Int) : List<GameResponseItem>

}