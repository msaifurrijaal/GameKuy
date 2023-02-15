package com.saifurrijaal.gamekuy.data.model

import com.google.gson.annotations.SerializedName

data class ScreenshotsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)