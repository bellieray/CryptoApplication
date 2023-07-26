package com.example.cryptoapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.databinding.ItemDialogRefreshTimeBinding

class RefreshTimeAdapter(val onItemClicked: (String) -> Unit) :
    ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback) {
    var selectedItemPosition: Int = RecyclerView.NO_POSITION

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RefreshTimeViewHolder(
            ItemDialogRefreshTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RefreshTimeViewHolder -> holder.bind(getItem(position))
        }
    }

    inner class RefreshTimeViewHolder(val binding: ItemDialogRefreshTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.tvRefreshTime.text = text
            binding.tvRefreshTime.isSelected = selectedItemPosition == adapterPosition
            binding.root.setOnClickListener {
                onItemClicked.invoke(text)
                selectedItemPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }
}