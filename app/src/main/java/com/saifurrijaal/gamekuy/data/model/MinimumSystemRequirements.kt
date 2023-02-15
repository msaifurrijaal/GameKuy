package com.saifurrijaal.gamekuy.data.model

import com.google.gson.annotations.SerializedName

data class MinimumSystemRequirements(

	@field:SerializedName("memory")
	val memory: String? = null,

	@field:SerializedName("os")
	val os: String? = null,

	@field:SerializedName("graphics")
	val graphics: String? = null,

	@field:SerializedName("storage")
	val storage: String? = null,

	@field:SerializedName("processor")
	val processor: String? = null
)