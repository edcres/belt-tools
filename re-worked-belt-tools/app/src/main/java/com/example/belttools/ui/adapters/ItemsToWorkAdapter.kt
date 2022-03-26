package com.example.belttools.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.belttools.data.model.entities.SKU
import com.example.belttools.databinding.ItemsToWorkItemBinding
import com.example.belttools.ui.viewmodel.SharedViewModel

class ItemsToWorkAdapter(
    private val viewModel: SharedViewModel
): ListAdapter<SKU, ItemsToWorkAdapter.ItemsToWorkViewHolder>(ItemsToWorkDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemsToWorkViewHolder.from(viewModel, parent)

    override fun onBindViewHolder(holder: ItemsToWorkViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ItemsToWorkViewHolder private constructor(
        private val viewModel: SharedViewModel,
        private val binding: ItemsToWorkItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(sKU: SKU) {

            binding.apply {

            }
        }

        companion object {
            fun from(
                viewModel: SharedViewModel,
                parent: ViewGroup
            ): ItemsToWorkViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemsToWorkItemBinding.inflate(layoutInflater, parent, false)
                return ItemsToWorkViewHolder(viewModel, binding)
            }
        }
    }
}

class ItemsToWorkDiffCallback : DiffUtil.ItemCallback<SKU>() {
    override fun areItemsTheSame(oldItem: SKU, newItem: SKU): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: SKU, newItem: SKU): Boolean {
        return oldItem == newItem
    }
}