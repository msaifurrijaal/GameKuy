package com.saifurrijaal.gamekuy.ui.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifurrijaal.gamekuy.R
import com.saifurrijaal.gamekuy.adapter.AllGamesAdapter
import com.saifurrijaal.gamekuy.databinding.ActivityGameCategoryBinding
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
        closeActivity()

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
        categoryName = intent.getStringExtra(CategoryFragment.CATEGORY)!!.toLowerCase(Locale.getDefault())
    }

}