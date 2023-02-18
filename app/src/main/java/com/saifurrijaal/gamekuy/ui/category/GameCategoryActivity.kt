package com.saifurrijaal.gamekuy.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifurrijaal.gamekuy.adapter.AllGamesAdapter
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.ActivityGameCategoryBinding
import com.saifurrijaal.gamekuy.ui.gamedetail.GameDetailActivity
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

        loadingCase()
        setGameCategoryRV()
        observerAllGameByCategory()
        onItemGameCategoryClick()
        onAction()

    }

    private fun onAction() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                finish()
            }

            etSearchMain.addTextChangedListener {
                gameCategoryAdapter.filter.filter(it.toString())
            }

            etSearchMain.setOnEditorActionListener {_, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val dataSearch = etSearchMain.text.toString().trim()
                    gameCategoryAdapter.filter.filter(dataSearch)
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener true
            }
        }
    }

    private fun onItemGameCategoryClick() {
        gameCategoryAdapter.onItemClick = {
            startActivity(
                Intent(this, GameDetailActivity::class.java)
                    .putExtra(Constant.GAME_ID, it.id)
                    .putExtra(Constant.TYPE_INTENT, Constant.GAME_API)
            )
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
        viewModel.gameCategory.observe(this, object : Observer<List<GameResponseItem>> {
            override fun onChanged(games: List<GameResponseItem>?) {
                responseCase()
                gameCategoryAdapter.gamesList = games!!.toMutableList()
            }
        })
    }

    private fun receiveCategoryFromIntent() {
        categoryName = intent.getStringExtra(Constant.CATEGORY)!!.toLowerCase(Locale.getDefault())
    }

    private fun loadingCase() {
        binding.apply {
            rvAllGames.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun responseCase() {
        binding.apply {
            rvAllGames.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

}