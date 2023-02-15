package com.saifurrijaal.gamekuy.ui.gamedetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.saifurrijaal.gamekuy.R
import com.saifurrijaal.gamekuy.data.database.GameDatabase
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.ActivityGameBinding
import com.saifurrijaal.gamekuy.ui.home.HomeFragment
import com.saifurrijaal.gamekuy.ui.home.HomeViewModel
import com.saifurrijaal.gamekuy.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameActivity : AppCompatActivity() {

    var gameId: Int = 0
    val db by lazy { GameDatabase }
    private lateinit var binding: ActivityGameBinding
    private lateinit var gameToSave: GameResponseItem
    private lateinit var officialPageLink: String
    private lateinit var viewModelFavorit: GameDetailViewModel
    val gameMvvm by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveIdGameFromIntent()

        val vmFactory = GameDetailVMFactory(gameId, application)
        viewModelFavorit = ViewModelProvider(this, vmFactory).get(GameDetailViewModel::class.java)

        setInformationGame()
        onAction()

    }

    private fun onFavoriteClick() {
        binding.btnAddToFavorite.setOnClickListener {
            gameToSave?.let {
                viewModelFavorit.upsertGameFavorit(it)
                Toast.makeText(this, "Meal Successfully Save!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun receiveIdGameFromIntent() {
        gameId = intent.getIntExtra(Constant.GAME_ID, 0)
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
        CoroutineScope(Dispatchers.IO).launch {
            gameToSave = db.getInstance(this@GameActivity).gameDao().getGame(gameId)[0]
            officialPageLink = gameToSave.gameUrl!!
            withContext(Dispatchers.Main) {
                Glide.with(this@GameActivity)
                    .load(gameToSave.thumbnail)
                    .into(binding.imgMovieDetail)

                binding.collapsingToolbar.title = gameToSave.title
                binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
                binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

                binding.tvPlatform.text = "Platform : ${gameToSave.platform}"
                binding.tvDescription.text = gameToSave.shortDescription
                binding.tvDeveloper.text = "Developer : ${gameToSave.developer}"
                binding.tvGenre.text = "Genre : ${gameToSave.genre}"
                binding.tvPublisher.text = "Publisher : ${gameToSave.publisher}"
                binding.tvReleaseDate.text = "Release : ${gameToSave.releaseDate}"
            }
        }
    }
}