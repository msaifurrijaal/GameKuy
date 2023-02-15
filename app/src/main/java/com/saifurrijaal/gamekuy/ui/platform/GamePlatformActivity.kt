package com.saifurrijaal.gamekuy.ui.platform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifurrijaal.gamekuy.adapter.AllGamesAdapter
import com.saifurrijaal.gamekuy.databinding.ActivityGamePlatformBinding
import com.saifurrijaal.gamekuy.ui.gamedetail.GameDetailActivity
import com.saifurrijaal.gamekuy.ui.home.HomeFragment
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

        setGamePlatformRV()
        observerAllGamesByPlatform()
        onItemPlatformGameClick()
        closeActivity()

    }

    private fun onItemPlatformGameClick() {
        gamePlatformAdapter.onItemClick = {
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

    private fun observerAllGamesByPlatform() {
        viewModel.gamePlatform.observe(this, Observer { games ->
            gamePlatformAdapter.setGames(games)
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
}