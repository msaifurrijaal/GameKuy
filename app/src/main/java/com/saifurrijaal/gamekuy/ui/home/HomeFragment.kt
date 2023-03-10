package com.saifurrijaal.gamekuy.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifurrijaal.gamekuy.adapter.GameAdapter
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.FragmentHomeBinding
import com.saifurrijaal.gamekuy.ui.gamedetail.GameDetailActivity
import com.saifurrijaal.gamekuy.ui.platform.GamePlatformActivity
import com.saifurrijaal.gamekuy.util.Constant
import kotlin.random.Random


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var allGamesAdapter: GameAdapter
    private lateinit var listGameHome: List<GameResponseItem>
    val homeMvvm by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAllGamesRV()
        observerAllGames()
        onItemGameClick()
        seeAllGames()
        onPlatformClick()
    }

    private fun onPlatformClick() {
        binding.apply {
            cvPlatform1.setOnClickListener {
                startActivity(Intent(activity, GamePlatformActivity::class.java)
                    .putExtra(Constant.PLATFORM, "pc"))
            }
            cvPlatform2.setOnClickListener {
                startActivity(Intent(activity, GamePlatformActivity::class.java)
                    .putExtra(Constant.PLATFORM, "browser"))
            }
        }
    }

    private fun seeAllGames() {
        binding.apply {
            tvSeeAll.setOnClickListener {
                startActivity(Intent(activity, AllGameActivity::class.java))
            }

            ivMainPoster.setOnClickListener {
                listGameHome?.let {
                    startActivity(Intent(activity, AllGameActivity::class.java))
                }
            }
        }

    }

    private fun onItemGameClick() {
        allGamesAdapter.onItemClick = {
            startActivity(Intent(activity, GameDetailActivity::class.java)
                .putExtra(Constant.GAME_ID, it.id)
                .putExtra(Constant.TYPE_INTENT, Constant.GAME_CACHE)
            )
        }
    }

    private fun observerAllGames() {
        val randomInt = Random.nextInt(1,6)
        homeMvvm.allGames.observe(viewLifecycleOwner, { games ->
            listGameHome = games.take(10)
            allGamesAdapter.setGames(listGameHome)
        })
    }

    private fun setAllGamesRV() {
        allGamesAdapter = GameAdapter()
        binding.rvListGames.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = allGamesAdapter
        }
    }

}