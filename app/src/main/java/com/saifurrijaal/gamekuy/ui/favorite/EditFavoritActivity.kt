package com.saifurrijaal.gamekuy.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.saifurrijaal.gamekuy.R
import com.saifurrijaal.gamekuy.data.model.GameFavoritItem
import com.saifurrijaal.gamekuy.databinding.ActivityEditFavoritBinding
import com.saifurrijaal.gamekuy.util.Constant

class EditFavoritActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditFavoritBinding
    private lateinit var game : GameFavoritItem
    private lateinit var viewModel: GameFavoritViewModel
    var gameToUpdate: GameFavoritItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GameFavoritViewModel::class.java)

        receiveGameFromIntent()
        setDataGame()
        closeActivity()
        onUpdateIconClick()
    }

    private fun onUpdateIconClick() {
        binding.fabSave.setOnClickListener {
            gameToUpdate = GameFavoritItem(
                id = game.id,
                title = binding.etTitleGame.text.toString(),
                thumbnail = game.thumbnail,
                shortDescription = binding.etDescriptionGame.text.toString(),
                gameUrl = game.gameUrl,
                genre = binding.etGenreGame.text.toString(),
                platform = binding.etPlatformGame.text.toString(),
                publisher = binding.etPublisherGame.text.toString(),
                developer = binding.etDeveloperGame.text.toString(),
                releaseDate = game.releaseDate,
                freetogameProfileUrl = game.freetogameProfileUrl
            )
            viewModel.updateGameFavorite(gameToUpdate!!)
            Toast.makeText(this, "${gameToUpdate!!.title} Successfully Update", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun closeActivity() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setDataGame() {
        binding.apply {
            etTitleGame.setText("${game.title}")
            etGenreGame.setText("${game.genre}")
            etDeveloperGame.setText("${game.developer}")
            etPlatformGame.setText("${game.platform}")
            etPublisherGame.setText("${game.publisher}")
            etDescriptionGame.setText("${game.shortDescription}")
        }
    }

    private fun receiveGameFromIntent() {
        game = intent.getParcelableExtra<GameFavoritItem>(Constant.GAME_OBJECT)!!
    }
}