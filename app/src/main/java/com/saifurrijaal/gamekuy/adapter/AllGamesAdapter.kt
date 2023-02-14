package com.saifurrijaal.gamekuy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.ItemGameBinding

class AllGamesAdapter() : RecyclerView.Adapter<AllGamesAdapter.ViewHolder>() {

    private var gamesList = ArrayList<GameResponseItem>()

    fun setMeals(gamesList: List<GameResponseItem>) {
        this.gamesList.clear()
        this.gamesList.addAll(gamesList)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var game = gamesList[position]
        Glide.with(holder.itemView)
            .load(game.thumbnail)
            .into(holder.binding.ivGame)

        holder.binding.tvGameTitle.text = game.title
        holder.binding.tvGameGenre.text = "Genre : ${game.genre}"

    }

    override fun getItemCount(): Int = gamesList.size
}