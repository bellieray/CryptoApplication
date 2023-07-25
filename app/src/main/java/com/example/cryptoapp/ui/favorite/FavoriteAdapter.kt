package com.example.cryptoapp.ui.favorite


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.model.FavoriteCrypto
import com.example.cryptocurrency.databinding.ItemFavoriteBinding

class FavoriteAdapter(val onItemClicked: (String?) -> Unit) :
    ListAdapter<FavoriteCrypto, RecyclerView.ViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<FavoriteCrypto>() {
        override fun areItemsTheSame(
            oldItem: FavoriteCrypto, newItem: FavoriteCrypto
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.price == newItem.price
        }

        override fun areContentsTheSame(
            oldItem: FavoriteCrypto, newItem: FavoriteCrypto
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteViewHolder -> holder.bind(getItem(position))
        }
    }

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(crypto: FavoriteCrypto) {
            binding.crypto = crypto
            binding.root.setOnClickListener {
                onItemClicked.invoke(crypto.id)
            }
        }
    }
}