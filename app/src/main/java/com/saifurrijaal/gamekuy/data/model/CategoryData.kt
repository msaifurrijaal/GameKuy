package com.saifurrijaal.gamekuy.data.model

import com.saifurrijaal.gamekuy.R

object CategoryData {
    private val categoryName = arrayOf(
        "Shooter",
        "MMORPG",
        "Strategy",
        "Moba",
        "Racing",
        "Sports",
        "Space",
        "Battle-Royale"
    )

    private val categoryPhoto = intArrayOf(
        R.drawable.shooter,
        R.drawable.mmorpg,
        R.drawable.strategy,
        R.drawable.moba,
        R.drawable.racing,
        R.drawable.sports,
        R.drawable.space,
        R.drawable.battleroyal
    )

    val listData : ArrayList<Category>
        get() {
            val list = arrayListOf<Category>()
            for (position in categoryName.indices) {
                val football = Category()
                football.name = categoryName[position]
                football.photo = categoryPhoto[position]
                list.add(football)
            }
            return list
        }
}