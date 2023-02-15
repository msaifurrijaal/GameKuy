package com.saifurrijaal.gamekuy.ui.category

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.saifurrijaal.gamekuy.R
import com.saifurrijaal.gamekuy.adapter.CategoryAdapter
import com.saifurrijaal.gamekuy.data.model.Category
import com.saifurrijaal.gamekuy.data.model.CategoryData
import com.saifurrijaal.gamekuy.databinding.FragmentCategoryBinding
import com.saifurrijaal.gamekuy.util.Constant

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private var list: ArrayList<Category> = arrayListOf()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list.addAll(CategoryData.listData)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCategoryRV()
        onCategoryItemClick()
    }

    private fun onCategoryItemClick() {
        categoryAdapter.onItemClick = {
            startActivity(Intent(activity, GameCategoryActivity::class.java)
                .putExtra(Constant.CATEGORY, it.name))
        }
    }

    private fun setCategoryRV() {
        categoryAdapter = CategoryAdapter()
        categoryAdapter.setCategory(list)
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

}