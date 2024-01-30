package com.example.homework_21.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_21.databinding.ItemShopLayoutBinding
import com.example.homework_21.presentation.model.Item

class ShopItemRecyclerAdapter : ListAdapter<Item, ShopItemRecyclerAdapter.ShopViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ItemShopLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShopViewHolder(private val binding: ItemShopLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            with(binding) {
                tvTitle.text = item.title
                tvPrice.text = item.price
                Glide.with(icImage.context)
                    .load(item.image)
                    .into(icImage)
                icHeart.visibility = if (item.favorite == Item.Favorite.FAVORITE) View.VISIBLE else View.GONE
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}