package com.saifurrijaal.gamekuy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gameFavoritItem")
data class GameFavoritItem (

    @field:SerializedName("short_description")
    val shortDescription: String? = null,

    @field:SerializedName("thumbnail")
    val thumbnail: String? = null,

    @field:SerializedName("game_url")
    val gameUrl: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("freetogame_profile_url")
    val freetogameProfileUrl: String? = null,

    @field:SerializedName("genre")
    val genre: String? = null,

    @field:SerializedName("publisher")
    val publisher: String? = null,

    @field:SerializedName("developer")
    val developer: String? = null,

    @field:SerializedName("id")
    @PrimaryKey
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("platform")
    val platform: String? = null
)