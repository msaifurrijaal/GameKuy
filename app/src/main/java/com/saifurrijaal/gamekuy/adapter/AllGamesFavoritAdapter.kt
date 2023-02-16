package com.saifurrijaal.gamekuy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifurrijaal.gamekuy.data.model.GameFavoritItem
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.ItemGameBinding
import com.saifurrijaal.gamekuy.databinding.ListItemGameFavoriteBinding

class AllGamesFavoritAdapter : RecyclerView.Adapter<AllGamesFavoritAdapter.ViewHolder>() {

    lateinit var onItemClick: ((GameFavoritItem) -> Unit)
    lateinit var onDeleteClick: ((GameFavoritItem) -> Unit)
    lateinit var onUpdateClick: ((GameFavoritItem) -> Unit)

    private var gamesList = ArrayList<GameFavoritItem>()

    fun setGames(gamesList: List<GameFavoritItem>) {
        this.gamesList.clear()
        this.gamesList.addAll(gamesList)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: ListItemGameFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemGameFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var game = gamesList[position]
        Glide.with(holder.itemView)
            .load(game.thumbnail)
            .into(holder.binding.ivGame)

        holder.binding.tvGameTitle.text = game.title
        holder.binding.tvGameGenre.text = "Genre : ${game.genre}"

        holder.itemView.setOnClickListener {
            onItemClick.invoke(game)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick.invoke(game)
        }

        holder.binding.btnEdit.setOnClickListener {
            onUpdateClick.invoke(game)
        }
    }

    override fun getItemCount(): Int = gamesList.size
}