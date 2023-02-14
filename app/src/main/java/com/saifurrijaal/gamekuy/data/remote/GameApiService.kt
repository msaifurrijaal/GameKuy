package com.saifurrijaal.gamekuy.data.remote

import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApiService {

    @GET("games")
    suspend fun getListGames() : Call<List<GameResponseItem>>

    @GET("games")
    fun getListByCategories(@Query("category") categoryName: String) : Call<List<GameResponseItem>>

    @GET("games")
    fun getListByPlatform(@Query("platform") platformName: String) : Call<List<GameResponseItem>>
}