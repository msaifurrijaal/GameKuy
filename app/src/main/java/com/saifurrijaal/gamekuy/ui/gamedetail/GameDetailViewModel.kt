package com.saifurrijaal.gamekuy.ui.gamedetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.saifurrijaal.gamekuy.data.database.GameDao
import com.saifurrijaal.gamekuy.data.database.GameDatabase
import com.saifurrijaal.gamekuy.data.database.GameFavoritDao
import com.saifurrijaal.gamekuy.data.model.GameDetailResponse
import com.saifurrijaal.gamekuy.data.model.GameFavoritItem
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.data.remote.RetrofitInstance
import com.saifurrijaal.gamekuy.repository.GameFavoritRepository
import com.saifurrijaal.gamekuy.repository.GameRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailViewModel(private var id: Int, application: Application) : AndroidViewModel(application) {

    private val gameFavoritRepository : GameFavoritRepository
    private val gameFavoritDao : GameFavoritDao

    private val gameRepository : GameRepository
    private val gameDao : GameDao

    private val _gameDetailApi = MutableLiveData<GameDetailResponse>()
    val gameDetailApi : LiveData<GameDetailResponse>
        get() = _gameDetailApi

    private var _gameDetailCache : LiveData<List<GameResponseItem>>
    val gameDetailCache : LiveData<List<GameResponseItem>>
        get() = _gameDetailCache

    private var _gameDetailFavorit : LiveData<List<GameFavoritItem>>
    val gameDetailFavorit : LiveData<List<GameFavoritItem>>
        get() = _gameDetailFavorit

    init {
        getDetailGame()

        gameFavoritDao = GameDatabase.getInstance(application).gameFavoritDao()
        gameFavoritRepository = GameFavoritRepository(gameFavoritDao)

        gameDao = GameDatabase.getInstance(application).gameDao()
        gameRepository = GameRepository(gameDao)

        _gameDetailCache = gameRepository.getGameItem(id)

        _gameDetailFavorit = gameFavoritRepository.getGameItem(id)
    }

    fun getDetailGame() {
        RetrofitInstance.getApiService().getGame(id).enqueue(object : Callback<GameDetailResponse> {
            override fun onResponse(
                call: Call<GameDetailResponse>,
                response: Response<GameDetailResponse>
            ) {
                if (response.isSuccessful) {
                    _gameDetailApi.value = response.body()
                } else {
                    Log.e("GameDetailViewModel", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GameDetailResponse>, t: Throwable) {
                Log.e("GameDetailViewModel", t.message.toString())
            }
        })
    }

    fun upsertGameFavorit(game: GameFavoritItem) {
        viewModelScope.launch {
            gameFavoritRepository.upsertGamesRepo(game)
        }
    }
}