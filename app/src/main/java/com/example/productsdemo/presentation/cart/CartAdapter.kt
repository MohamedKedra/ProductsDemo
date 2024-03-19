package com.example.productsdemo.presentation.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsdemo.R
import com.example.productsdemo.data.local.Item
import com.example.productsdemo.databinding.ItemCartBinding

class CartAdapter(private val context: Context, val onItemClick: (Item) -> Unit) :
    ListAdapter<Item, CartAdapter.ProductHolder>(ProductDiffCallback()) {

    inner class ProductHolder(private val itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.root), View.OnClickListener {

        init {
            itemCartBinding.btnDelete.setOnClickListener(this)
        }

        fun bind(item: Item) {
            with(itemCartBinding) {
                item.apply {
                    Glide.with(context).load(image).placeholder(
                        R.drawable.ic_item)
                        .into(ivProductImg)

                    tvName.text = title
                    tvPrice.text = (price * count).toString().plus(context.getString(R.string.currency))
                    tvQuantity.text = context.getString(R.string.quantity).plus(count)
                }
            }
        }

        override fun onClick(p0: View?) {
            onItemClick.invoke(getItem(adapterPosition))
            notifyItemRemoved(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(view)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class ProductDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}