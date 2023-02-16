package com.saifurrijaal.gamekuy.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifurrijaal.gamekuy.R
import com.saifurrijaal.gamekuy.adapter.AllGamesAdapter
import com.saifurrijaal.gamekuy.adapter.AllGamesFavoritAdapter
import com.saifurrijaal.gamekuy.data.model.GameFavoritItem
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.FragmentFavoriteBinding
import com.saifurrijaal.gamekuy.databinding.FragmentHomeBinding
import com.saifurrijaal.gamekuy.ui.gamedetail.GameDetailActivity
import com.saifurrijaal.gamekuy.util.Constant

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var allGamesAdapter: AllGamesFavoritAdapter
    private lateinit var viewModel: GameFavoritViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(GameFavoritViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingCase()
        setupGameFavoritRV()
        observerAllGamesFavorit()
        onItemGameFavoriteClick()
        onDeleteIconClick()
        onUpdateIconClick()

    }

    private fun onItemGameFavoriteClick() {
        allGamesAdapter.onItemClick = {
            startActivity(Intent(activity, GameDetailActivity::class.java)
                .putExtra(Constant.GAME_ID, it.id)
                .putExtra(Constant.TYPE_INTENT, Constant.GAME_FAV)
            )
        }
    }

    private fun onUpdateIconClick() {
        allGamesAdapter.onUpdateClick = {
            startActivity(Intent(activity, EditFavoritActivity::class.java)
                .putExtra(Constant.GAME_OBJECT, it)
            )
        }
    }

    private fun onDeleteIconClick() {
        allGamesAdapter.onDeleteClick = {
            viewModel.deleteGameFavorite(it)
            Toast.makeText(activity, "${it.title} game has been removed", Toast.LENGTH_LONG).show()
        }
    }

    private fun observerAllGamesFavorit() {
        viewModel.allGames.observe(viewLifecycleOwner, object : Observer<List<GameFavoritItem>> {
            override fun onChanged(games: List<GameFavoritItem>?) {
                games?.let {
                    if (games.size != 0) {
                        responseCase()
                    }
                    allGamesAdapter.setGames(games)
                }
            }
        })
    }

    private fun setupGameFavoritRV() {
        allGamesAdapter = AllGamesFavoritAdapter()
        binding.rvAllGames.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = allGamesAdapter
        }
    }

    private fun loadingCase() {
        binding.apply {
            rvAllGames.visibility = View.INVISIBLE
            ivEmpty.visibility = View.VISIBLE
            tvEmpty.visibility = View.VISIBLE
        }
    }

    private fun responseCase() {
        binding.apply {
            rvAllGames.visibility = View.VISIBLE
            ivEmpty.visibility = View.INVISIBLE
            tvEmpty.visibility = View.INVISIBLE
        }
    }

}