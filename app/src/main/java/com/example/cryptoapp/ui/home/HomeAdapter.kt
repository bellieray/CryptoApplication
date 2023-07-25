package com.example.cryptoapp.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.model.Crypto
import com.example.cryptocurrency.databinding.ItemHomeCryptoBinding

class HomeListAdapter(val onItemClicked: () -> Unit) :
    ListAdapter<Crypto, RecyclerView.ViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<Crypto>() {
        override fun areItemsTheSame(
            oldItem: Crypto,
            newItem: Crypto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Crypto,
            newItem: Crypto
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(
            ItemHomeCryptoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> holder.bind(getItem(position))
        }
    }

    inner class HomeViewHolder(val binding: ItemHomeCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(crypto: Crypto) {
            binding.crypto = crypto
            binding.root.setOnClickListener {
                onItemClicked.invoke()
            }
        }
    }
}