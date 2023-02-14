package com.saifurrijaal.gamekuy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saifurrijaal.gamekuy.data.model.GameFavoritItem
import com.saifurrijaal.gamekuy.data.model.GameResponseItem

@Database(entities = [GameResponseItem::class, GameFavoritItem::class], version = 1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun gameFavoritDao(): GameFavoritDao

    companion object {
        @Volatile
        var INSTANCE: GameDatabase? = null

        @Synchronized
        fun getInstance(context: Context): GameDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    GameDatabase::class.java,
                    "game_db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as GameDatabase
        }
    }
}