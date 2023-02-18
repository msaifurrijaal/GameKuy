package com.saifurrijaal.gamekuy.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifurrijaal.gamekuy.adapter.AllGamesAdapter
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.ActivityAllGameBinding
import com.saifurrijaal.gamekuy.ui.gamedetail.GameDetailActivity
import com.saifurrijaal.gamekuy.util.Constant

class AllGameActivity : AppCompatActivity() {

    val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: ActivityAllGameBinding
    private lateinit var allGamesAdapter: AllGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingCase()
        setAllGamesRV()
        observerAllGames()
        onItemGameClick()
        onAction()

    }

    private fun onAction() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                finish()
            }

            etSearchMain.addTextChangedListener {
                allGamesAdapter.filter.filter(it.toString())
            }

            etSearchMain.setOnEditorActionListener {_, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val dataSearch = etSearchMain.text.toString().trim()
                    allGamesAdapter.filter.filter(dataSearch)
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener true
            }
        }
    }

    private fun onItemGameClick() {
        allGamesAdapter.onItemClick = {
            startActivity(
                Intent(this, GameDetailActivity::class.java)
                    .putExtra(Constant.GAME_ID, it.id)
                    .putExtra(Constant.TYPE_INTENT, Constant.GAME_CACHE)
            )
        }
    }

    private fun observerAllGames() {
        viewModel.allGames.observe(this, object : Observer<List<GameResponseItem>> {
            override fun onChanged(games: List<GameResponseItem>?) {
                responseCase()
                allGamesAdapter.gamesList = games!!.toMutableList()
            }
        })
    }

    private fun setAllGamesRV() {
        allGamesAdapter = AllGamesAdapter()
        binding.rvAllGames.apply {
            layoutManager = LinearLayoutManager(this@AllGameActivity, LinearLayoutManager.VERTICAL, false)
            adapter = allGamesAdapter
        }
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