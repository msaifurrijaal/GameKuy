package com.saifurrijaal.gamekuy.repository

import android.util.Log
import com.saifurrijaal.gamekuy.data.database.GameDao
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameRepository(private val gameDao: GameDao, ) {

    private lateinit var gamesData: List<GameResponseItem>

    val games = gameDao.getGames()

    suspend fun refreshGame() {
        withContext(Dispatchers.IO) {
            RetrofitInstance.getApiService().getListGames().enqueue(object :
                Callback<List<GameResponseItem>> {
                override fun onResponse(
                    call: Call<List<GameResponseItem>>,
                    response: Response<List<GameResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        gamesData = response.body()!!
                    } else {
                        Log.e("GamesRepository", "onFailure : ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<GameResponseItem>>, t: Throwable) {
                    Log.e("GameRepository", "onFailure : ${t.message.toString()}")
                }
            })
            gameDao.upsertMeal(gamesData!!)
        }
    }
}