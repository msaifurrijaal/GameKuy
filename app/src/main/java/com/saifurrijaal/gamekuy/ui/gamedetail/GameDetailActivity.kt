package com.saifurrijaal.gamekuy.ui.gamedetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.saifurrijaal.gamekuy.R
import com.saifurrijaal.gamekuy.data.model.GameDetailResponse
import com.saifurrijaal.gamekuy.data.model.GameFavoritItem
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.ActivityGameDetailBinding
import com.saifurrijaal.gamekuy.util.Constant

class GameDetailActivity : AppCompatActivity() {

    var gameId: Int = 0
    private lateinit var binding: ActivityGameDetailBinding
    private lateinit var typeIntent: String
    private lateinit var officialPageLink: String
    private lateinit var viewModel: GameDetailViewModel
    private var gameToSave: GameFavoritItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveIdGameFromIntent()
        loadingCase()

        val vmFactory = GameDetailVMFactory(gameId, application)
        viewModel = ViewModelProvider(this, vmFactory).get(GameDetailViewModel::class.java)

        setInformationGame()
        onAction()

    }

    private fun onFavoriteClick() {
        binding.btnAddToFavorite.setOnClickListener {
            gameToSave?.let {
                viewModel.upsertGameFavorit(it)
                Toast.makeText(this, "Game successfully add to favorite!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun receiveIdGameFromIntent() {
        gameId = intent.getIntExtra(Constant.GAME_ID, 0)
        typeIntent = intent.getStringExtra(Constant.TYPE_INTENT)!!
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

    private fun setInformationGame() {
        if (typeIntent.equals(Constant.GAME_CACHE)) {
            viewModel.gameDetailCache.observe(this, object : Observer<List<GameResponseItem>> {
                override fun onChanged(games: List<GameResponseItem>?) {
                    responseCase()
                    var game = games!![0]
                    gameToSave = GameFavoritItem(
                        shortDescription = game.shortDescription,
                        thumbnail = game.thumbnail,
                        gameUrl = game.gameUrl,
                        releaseDate = game.releaseDate,
                        freetogameProfileUrl = game.freetogameProfileUrl,
                        genre = game.genre,
                        publisher = game.publisher,
                        developer = game.developer,
                        id = game.id,
                        title = game.title,
                        platform = game.platform
                    )
                    officialPageLink = game.gameUrl!!

                    Glide.with(this@GameDetailActivity)
                        .load(game.thumbnail)
                        .into(binding.imgMovieDetail)

                    binding.collapsingToolbar.title = game.title
                    binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
                    binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

                    binding.tvPlatform.text = "Platform : ${game.platform}"
                    binding.tvDescription.text = game.shortDescription
                    binding.tvDeveloper.text = "Developer : ${game.developer}"
                    binding.tvGenre.text = "Genre : ${game.genre}"
                    binding.tvPublisher.text = "Publisher : ${game.publisher}"
                    binding.tvReleaseDate.text = "Release : ${game.releaseDate}"
                }
            })
        }
        else if (typeIntent.equals(Constant.GAME_API)) {
            viewModel.gameDetailApi.observe(this, object : Observer<GameDetailResponse> {
                override fun onChanged(game: GameDetailResponse?) {
                    responseCase()
                    officialPageLink = game!!.gameUrl!!
                    Glide.with(this@GameDetailActivity)
                        .load(game.thumbnail)
                        .into(binding.imgMovieDetail)

                    binding.collapsingToolbar.title = game.title
                    binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
                    binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

                    binding.tvPlatform.text = "Platform : ${game.platform}"
                    binding.tvDescription.text = game.description
                    binding.tvDeveloper.text = "Developer : ${game.developer}"
                    binding.tvGenre.text = "Genre : ${game.genre}"
                    binding.tvPublisher.text = "Publisher : ${game.publisher}"
                    binding.tvReleaseDate.text = "Release : ${game.releaseDate}"

                    gameToSave = GameFavoritItem(
                        shortDescription = game.description,
                        thumbnail = game.thumbnail,
                        gameUrl = game.gameUrl,
                        releaseDate = game.releaseDate,
                        freetogameProfileUrl = game.freetogameProfileUrl,
                        genre = game.genre,
                        publisher = game.publisher,
                        developer = game.developer,
                        id = game.id,
                        title = game.title,
                        platform = game.platform
                    )
                }
            })
        } else if (typeIntent.equals(Constant.GAME_FAV)) {

        }
    }

    private fun loadingCase() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            tvPlatform.visibility = View.INVISIBLE
            tvOverviewTitle.visibility = View.INVISIBLE
            tvDescription.visibility = View.INVISIBLE
            tvReleaseDate.visibility = View.INVISIBLE
            tvGenre.visibility = View.INVISIBLE
            tvDeveloper.visibility = View.INVISIBLE
            tvPublisher.visibility = View.INVISIBLE
            btnGoesToPage.visibility = View.INVISIBLE
        }
    }

    private fun responseCase() {
        binding.apply {
            progressBar.visibility = View.INVISIBLE
            tvPlatform.visibility = View.VISIBLE
            tvOverviewTitle.visibility = View.VISIBLE
            tvDescription.visibility = View.VISIBLE
            tvReleaseDate.visibility = View.VISIBLE
            tvGenre.visibility = View.VISIBLE
            tvDeveloper.visibility = View.VISIBLE
            tvPublisher.visibility = View.VISIBLE
            btnGoesToPage.visibility = View.VISIBLE
        }
    }
}