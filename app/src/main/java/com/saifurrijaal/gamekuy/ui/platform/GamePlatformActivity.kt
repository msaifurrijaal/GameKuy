package com.saifurrijaal.gamekuy.ui.platform

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
import com.saifurrijaal.gamekuy.databinding.ActivityGamePlatformBinding
import com.saifurrijaal.gamekuy.ui.gamedetail.GameDetailActivity
import com.saifurrijaal.gamekuy.util.Constant

class GamePlatformActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamePlatformBinding
    private lateinit var platformGame: String
    private lateinit var viewModel: GamePlatformViewModel
    private lateinit var gamePlatformAdapter: AllGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamePlatformBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receivePlatformFromIntent()

        val vmFactory = GamePlatformVMFactory(platformGame)
        viewModel = ViewModelProvider(this, vmFactory).get(GamePlatformViewModel::class.java)

        loadingCase()
        setGamePlatformRV()
        observerAllGamesByPlatform()
        onItemPlatformGameClick()
        onAction()

    }

    private fun onAction() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                finish()
            }

            etSearchMain.addTextChangedListener {
                gamePlatformAdapter.filter.filter(it.toString())
            }

            etSearchMain.setOnEditorActionListener {_, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val dataSearch = etSearchMain.text.toString().trim()
                    gamePlatformAdapter.filter.filter(dataSearch)
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener true
            }
        }
    }

    private fun onItemPlatformGameClick() {
        gamePlatformAdapter.onItemClick = {
            startActivity(
                Intent(this, GameDetailActivity::class.java)
                    .putExtra(Constant.GAME_ID, it.id)
                    .putExtra(Constant.TYPE_INTENT, Constant.GAME_API)
            )
        }
    }

    private fun observerAllGamesByPlatform() {
        viewModel.gamePlatform.observe(this, object : Observer<List<GameResponseItem>> {
            override fun onChanged(games: List<GameResponseItem>?) {
                responseCase()
                gamePlatformAdapter.gamesList = games!!.toMutableList()
            }
        })
    }

    private fun setGamePlatformRV() {
        binding.tvAllGames.text = "Best Game from ${platformGame} Platform"
        gamePlatformAdapter = AllGamesAdapter()
        binding.rvAllGames.apply {
            layoutManager = LinearLayoutManager(this@GamePlatformActivity, LinearLayoutManager.VERTICAL, false)
            adapter = gamePlatformAdapter
        }
    }

    private fun receivePlatformFromIntent() {
        platformGame = intent.getStringExtra(Constant.PLATFORM)!!
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