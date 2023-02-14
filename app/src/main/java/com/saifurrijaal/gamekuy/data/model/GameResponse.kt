package com.saifurrijaal.gamekuy.data.model

import com.google.gson.annotations.SerializedName

data class GameResponse(

	@field:SerializedName("GameResponse")
	val gameResponse: List<GameResponseItem?>? = null
)