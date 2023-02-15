package com.saifurrijaal.gamekuy.ui.gamedetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.saifurrijaal.gamekuy.R
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.ActivityGameDetailBinding
import com.saifurrijaal.gamekuy.util.Constant

class GameDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameDetailBinding
    var gameId: Int = 0
    private lateinit var viewModel: GameDetailViewModel
    private lateinit var gameToSave: GameResponseItem
    private lateinit var officialPageLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveIdGameFromIntent()

        val vmFactory = GameDetailVMFactory(gameId, application)
        viewModel = ViewModelProvider(this, vmFactory).get(GameDetailViewModel::class.java)

        observerDetailGame()
        onAction()
    }

    private fun onFavoriteClick() {
        binding.btnAddToFavorite.setOnClickListener {
            gameToSave?.let {
                viewModel.upsertGameFavorit(it)
                Toast.makeText(this, "Meal Successfully Save!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onAction() {
        binding.apply {
            btnCloseGameDetail.setOnClickListener {
                finish()
            }

            btnGoesToPage.setOnClickListener {
                officialPageLink?.let {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(officialPageLink)))
                }
            }

            btnAddToFavorite.setOnClickListener {
                onFavoriteClick()
            }
        }
    }

    private fun observerDetailGame() {
        viewModel.gameDetail.observe(this, Observer { games ->
            Log.d("GameDetailActivity", "response : ${games}")
            officialPageLink = games.gameUrl!!
            Glide.with(this@GameDetailActivity)
                .load(games.thumbnail)
                .into(binding.imgMovieDetail)

            binding.collapsingToolbar.title = games.title
            binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
            binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

            binding.tvPlatform.text = "Platform : ${games.platform}"
            binding.tvDescription.text = games.description
            binding.tvDeveloper.text = "Developer : ${games.developer}"
            binding.tvGenre.text = "Genre : ${games.genre}"
            binding.tvPublisher.text = "Publisher : ${games.publisher}"
            binding.tvReleaseDate.text = "Release : ${games.releaseDate}"

            gameToSave = GameResponseItem(
                shortDescription = games.description,
                thumbnail = games.thumbnail,
                gameUrl = games.gameUrl,
                releaseDate = games.releaseDate,
                freetogameProfileUrl = games.freetogameProfileUrl,
                genre = games.genre,
                publisher = games.publisher,
                developer = games.developer,
                id = games.id,
                title = games.title,
                platform = games.platform
            )
        })
    }

    private fun receiveIdGameFromIntent() {
        gameId = intent.getIntExtra(Constant.GAME_ID, 0)
    }
}