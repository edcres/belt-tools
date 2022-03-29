package com.example.belttools.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.belttools.data.model.entities.SKU
import com.example.belttools.databinding.ItemsToWorkItemBinding
import com.example.belttools.ui.viewmodel.SharedViewModel

class ItemsToWorkAdapter(
    private val viewModel: SharedViewModel,
    private val fragLifecycleOwner: LifecycleOwner
): ListAdapter<SKU, ItemsToWorkAdapter.ItemsToWorkViewHolder>(ItemsToWorkDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemsToWorkViewHolder.from(viewModel, fragLifecycleOwner ,parent)

    override fun onBindViewHolder(holder: ItemsToWorkViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ItemsToWorkViewHolder private constructor(
        private val viewModel: SharedViewModel,
        private val fragLifecycleOwner: LifecycleOwner,
        private val binding: ItemsToWorkItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(sKU: SKU) {
            binding.apply {
                skuTxt.text = sKU.id.toString()
                noteEt.setText(sKU.notes)
                saveBtn.setOnClickListener {
                    sKU.notes = noteEt.text.toString()
                    viewModel.updateSKU(sKU)
                }
                deleteBtn.setOnClickListener {
                    viewModel.deletePalletOrFloorSku(sKU)
                    viewModel.toggleEditBtnOff()
                }
                viewModel.menuEditIsOn.observe(fragLifecycleOwner) { isOn ->
                    if (isOn) {
                        saveBtn.visibility = View.GONE
                        deleteBtn.visibility = View.VISIBLE
                    } else {
                        deleteBtn.visibility = View.GONE
                        saveBtn.visibility = View.VISIBLE
                    }
                }
            }
        }
        companion object {
            fun from(
                viewModel: SharedViewModel,
                fragLifecycleOwner: LifecycleOwner,
                parent: ViewGroup
            ): ItemsToWorkViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemsToWorkItemBinding.inflate(layoutInflater, parent, false)
                return ItemsToWorkViewHolder(viewModel, fragLifecycleOwner, binding)
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