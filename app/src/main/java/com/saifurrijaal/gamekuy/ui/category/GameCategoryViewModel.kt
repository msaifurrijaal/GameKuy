package com.saifurrijaal.gamekuy.ui.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.data.remote.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameCategoryViewModel(val category: String) : ViewModel() {

    private val _gameCategory = MutableLiveData<List<GameResponseItem>>()
    val gameCategory : LiveData<List<GameResponseItem>>
        get() = _gameCategory

    init {
        getGameByCategory()
    }

    fun getGameByCategory() {
        RetrofitInstance.getApiService().getListByCategories(category).enqueue(object: Callback<List<GameResponseItem>> {
            override fun onResponse(
                call: Call<List<GameResponseItem>>,
                response: Response<List<GameResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _gameCategory.value = response.body()
                } else {
                    Log.e("GameCategoryViewModel", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GameResponseItem>>, t: Throwable) {
                Log.e("GameCategoryViewModel", t.message.toString())
            }

        })
    }
}