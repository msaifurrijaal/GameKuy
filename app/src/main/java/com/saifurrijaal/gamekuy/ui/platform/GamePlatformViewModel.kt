package com.saifurrijaal.gamekuy.ui.platform

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.data.remote.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GamePlatformViewModel(val platform: String) : ViewModel() {

    private val _gamePlatform = MutableLiveData<List<GameResponseItem>>()
    val gamePlatform : LiveData<List<GameResponseItem>>
        get() = _gamePlatform

    init {
        getGameByPlatform()
    }

    fun getGameByPlatform() {
        RetrofitInstance.getApiService().getListByPlatform(platform).enqueue(object : Callback<List<GameResponseItem>> {
            override fun onResponse(
                call: Call<List<GameResponseItem>>,
                response: Response<List<GameResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _gamePlatform.value = response.body()
                } else {
                    Log.e("GamePlatformViewModel", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GameResponseItem>>, t: Throwable) {
                Log.e("GamePlatformViewModel", t.message.toString())
            }

        })
    }
}