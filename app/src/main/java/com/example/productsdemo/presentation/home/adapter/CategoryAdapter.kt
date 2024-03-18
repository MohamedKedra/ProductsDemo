package com.example.productsdemo.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.productsdemo.R
import com.example.productsdemo.data.remote.models.Category
import com.example.productsdemo.databinding.ItemCategoryBinding

class CategoryAdapter(private val context: Context, val onItemClick: (Category) -> Unit) :
    ListAdapter<Category, CategoryAdapter.CategoryHolder>(CategoryDiffCallback()) {

    inner class CategoryHolder(private val itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(category: Category) {
            with(itemCategoryBinding) {
                category.apply {
                    tvCategory.text = title
                    tvCategory.setBackgroundResource(if (isSelected) R.drawable.bg_accent_layout else R.drawable.bg_outlined_container)
                    tvCategory.setTextColor(
                        if (isSelected) context.getColor(R.color.white) else context.getColor(
                            R.color.black
                        )
                    )
                }

            }
        }

        override fun onClick(p0: View?) {
            reset()
            val item = getItem(adapterPosition)
            item.isSelected = true
            onItemClick.invoke(item)
            notifyDataSetChanged()
        }

        private fun reset(){
            currentList.forEach { it.isSelected = false }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}