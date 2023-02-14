package com.saifurrijaal.gamekuy.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.saifurrijaal.gamekuy.R
import com.saifurrijaal.gamekuy.adapter.AllGamesAdapter
import com.saifurrijaal.gamekuy.databinding.ActivityAllGameBinding
import com.saifurrijaal.gamekuy.ui.gamedetail.GameActivity

class AllGameActivity : AppCompatActivity() {

    val gameMvvm by viewModels<HomeViewModel>()
    private lateinit var binding: ActivityAllGameBinding
    private lateinit var allGamesAdapter: AllGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAllGamesRV()
        observerAllGames()
        onItemGameClick()
        closeActivity()
    }

    private fun closeActivity() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun onItemGameClick() {
        allGamesAdapter.onItemClick = {
            startActivity(
                Intent(this, GameActivity::class.java)
                .putExtra(HomeFragment.GAME_ID, it.id))
        }
    }

    private fun observerAllGames() {
        gameMvvm.allGames.observe(this, { games ->
            allGamesAdapter.setGames(games)
        })
    }

    private fun setAllGamesRV() {
        allGamesAdapter = AllGamesAdapter()
        binding.rvAllGames.apply {
            layoutManager = LinearLayoutManager(this@AllGameActivity, LinearLayoutManager.VERTICAL, false)
            adapter = allGamesAdapter
        }
    }
}