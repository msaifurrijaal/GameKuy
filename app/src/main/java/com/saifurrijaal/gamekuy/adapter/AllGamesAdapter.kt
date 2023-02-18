package com.saifurrijaal.gamekuy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifurrijaal.gamekuy.data.model.GameResponseItem
import com.saifurrijaal.gamekuy.databinding.ListItemGameBinding

class AllGamesAdapter : RecyclerView.Adapter<AllGamesAdapter.ViewHolder>(), Filterable {

    lateinit var onItemClick: ((GameResponseItem) -> Unit)

    var gamesList = mutableListOf<GameResponseItem>()
        set(value) {
            field = value
            gamesFilter = value
            notifyDataSetChanged()
        }

    private var gamesFilter = mutableListOf<GameResponseItem>()

    private val filters = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList = mutableListOf<GameResponseItem>()
            val filterPattern = constraint.toString().trim().lowercase()

            if (filterPattern.isEmpty()){
                filteredList = gamesList
            }else{
                for (material in gamesList){
                    val title = material.title?.trim()?.lowercase()

                    if (title?.contains(filterPattern) == true){
                        filteredList.add(material)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            gamesFilter = results?.values as MutableList<GameResponseItem>
            notifyDataSetChanged()
        }
    }


//    fun setGames(gamesList: List<GameResponseItem>) {
//        this.gamesList.clear()
//        this.gamesList.addAll(gamesList)
//        notifyDataSetChanged()
//    }

    class ViewHolder(var binding: ListItemGameBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var game = gamesFilter[position]
        Glide.with(holder.itemView)
            .load(game.thumbnail)
            .into(holder.binding.ivGame)

        holder.binding.tvGameTitle.text = game.title
        holder.binding.tvGameGenre.text = "Genre : ${game.genre}"

        holder.itemView.setOnClickListener {
            onItemClick.invoke(game)
        }
    }

    override fun getItemCount(): Int = gamesFilter.size

    override fun getFilter(): Filter = filters

}