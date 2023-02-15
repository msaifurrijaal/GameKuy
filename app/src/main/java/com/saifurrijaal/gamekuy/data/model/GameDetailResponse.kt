package com.saifurrijaal.gamekuy.data.model

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(

	@field:SerializedName("short_description")
	val shortDescription: String? = null,

	@field:SerializedName("minimum_system_requirements")
	val minimumSystemRequirements: MinimumSystemRequirements? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("freetogame_profile_url")
	val freetogameProfileUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("platform")
	val platform: String? = null,

	@field:SerializedName("screenshots")
	val screenshots: List<ScreenshotsItem?>? = null,

	@field:SerializedName("game_url")
	val gameUrl: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("genre")
	val genre: String? = null,

	@field:SerializedName("publisher")
	val publisher: String? = null,

	@field:SerializedName("developer")
	val developer: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)