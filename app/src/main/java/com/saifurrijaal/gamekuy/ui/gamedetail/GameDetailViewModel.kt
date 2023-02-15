package com.saifurrijaal.gamekuy.ui.gamedetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.saifurrijaal.gamekuy.data.database.GameDao
import com.saifurrijaal.gamekuy.data.database.GameDatabase
import com.saifurrijaal.gamekuy.data.database.GameFavoritDao
import com.saifurrijaal.gamekuy.data.model.GameDetailResponse
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

    private val _gameDetail = MutableLiveData<GameDetailResponse>()
    val gameDetail : LiveData<GameDetailResponse>
        get() = _gameDetail

    init {
        getDetailGame()

        gameFavoritDao = GameDatabase.getInstance(application).gameFavoritDao()
        gameFavoritRepository = GameFavoritRepository(gameFavoritDao)

    }

    fun getDetailGame() {
        RetrofitInstance.getApiService().getGame(id).enqueue(object : Callback<GameDetailResponse> {
            override fun onResponse(
                call: Call<GameDetailResponse>,
                response: Response<GameDetailResponse>
            ) {
                if (response.isSuccessful) {
                    _gameDetail.value = response.body()
                } else {
                    Log.e("GameDetailViewModel", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GameDetailResponse>, t: Throwable) {
                Log.e("GameDetailViewModel", t.message.toString())
            }
        })
    }

    fun upsertGameFavorit(game: GameResponseItem) {
        viewModelScope.launch {
            gameFavoritRepository.upsertGamesRepo(game)
        }
    }
}