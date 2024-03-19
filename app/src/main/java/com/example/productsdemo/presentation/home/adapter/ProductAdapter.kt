package com.example.productsdemo.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsdemo.R
import com.example.productsdemo.data.remote.models.Product
import com.example.productsdemo.databinding.ItemProductBinding


class ProductAdapter(private val context: Context, val onItemClick: (Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductHolder>(ProductDiffCallback()) {

    inner class ProductHolder(private val itemProductBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemProductBinding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(product: Product) {
            with(itemProductBinding) {
                product.apply {
                    Glide.with(context).load(image).placeholder(
                        R.drawable.ic_item)
                        .into(ivProductImg)

                    tvName.text = title
                    tvPrice.text = price.toString()
                    tvCategory.text = category
                }
            }
        }

        override fun onClick(p0: View?) {
            onItemClick.invoke(getItem(adapterPosition))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(view)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}