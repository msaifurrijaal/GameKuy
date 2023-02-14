package com.saifurrijaal.gamekuy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifurrijaal.gamekuy.data.model.Category
import com.saifurrijaal.gamekuy.databinding.ItemCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    lateinit var onItemClick: ((Category) -> Unit)

    private var categoryList = ArrayList<Category>()

    fun setCategory(categoryList: List<Category>) {
        this.categoryList.clear()
        this.categoryList.addAll(categoryList)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = categoryList[position]
        Glide.with(holder.itemView)
            .load(category.photo)
            .into(holder.binding.ivGame)

        holder.binding.tvGameTitle.text = category.name

        holder.itemView.setOnClickListener {
            onItemClick.invoke(category)
        }
    }

    override fun getItemCount(): Int = categoryList.size
}