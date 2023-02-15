package com.saifurrijaal.gamekuy.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifurrijaal.gamekuy.R
import com.saifurrijaal.gamekuy.adapter.AllGamesAdapter
import com.saifurrijaal.gamekuy.databinding.ActivityGameCategoryBinding
import com.saifurrijaal.gamekuy.ui.gamedetail.GameActivity
import com.saifurrijaal.gamekuy.ui.gamedetail.GameDetailActivity
import com.saifurrijaal.gamekuy.ui.home.HomeFragment
import com.saifurrijaal.gamekuy.util.Constant
import java.util.*

class GameCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameCategoryBinding
    private lateinit var categoryName: String
    private lateinit var viewModel: GameCategoryViewModel
    private lateinit var gameCategoryAdapter: AllGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveCategoryFromIntent()

        val vmFactory = GameCategoryVMFactory(categoryName)
        viewModel = ViewModelProvider(this, vmFactory).get(GameCategoryViewModel::class.java)

        setGameCategoryRV()
        observerAllGameByCategory()
        onItemGameCategoryClick()
        closeActivity()

    }

    private fun onItemGameCategoryClick() {
        gameCategoryAdapter.onItemClick = {
            startActivity(
                Intent(this, GameDetailActivity::class.java)
                    .putExtra(Constant.GAME_ID, it.id)
            )
        }
    }

    private fun closeActivity() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setGameCategoryRV() {
        binding.tvAllGames.text = "Best Game from ${categoryName} Category"
        gameCategoryAdapter = AllGamesAdapter()
        binding.rvAllGames.apply {
            layoutManager = LinearLayoutManager(this@GameCategoryActivity, LinearLayoutManager.VERTICAL, false)
            adapter = gameCategoryAdapter
        }
    }

    private fun observerAllGameByCategory() {
        viewModel.gameCategory.observe(this, { games ->
            gameCategoryAdapter.setGames(games)
        })
    }

    private fun receiveCategoryFromIntent() {
        categoryName = intent.getStringExtra(Constant.CATEGORY)!!.toLowerCase(Locale.getDefault())
    }

}