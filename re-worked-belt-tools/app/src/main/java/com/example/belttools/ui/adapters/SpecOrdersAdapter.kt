package com.example.belttools.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.belttools.data.model.entities.SpecialtyOrder
import com.example.belttools.databinding.SpecialtyOrderItemBinding
import com.example.belttools.ui.viewmodel.SharedViewModel

class SpecOrdersAdapter(
    private val viewModel: SharedViewModel,
): ListAdapter<SpecialtyOrder, SpecOrdersAdapter.SpecOrdersViewHolder>(SpecOrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SpecOrdersViewHolder.from(viewModel, parent)

    override fun onBindViewHolder(holderWorkouts: SpecOrdersViewHolder, position: Int) =
        holderWorkouts.bind(getItem(position))

    class SpecOrdersViewHolder private constructor(
        private val viewModel: SharedViewModel,
        private val binding: SpecialtyOrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(specialtyOrder: SpecialtyOrder) {
            binding.apply {
                orderIdTxt.text = specialtyOrder.id.toString()
                orderNumberTxt.text = specialtyOrder.orderNum
                orderNoteTxt.text = specialtyOrder.note

                orderRecyclerItemContainer.setOnClickListener {
                    // todo: display order in the view
                    //  maybe do it through the the fragment and have an onClick interface
                }
            }
        }

        companion object {
            fun from(
                viewModel: SharedViewModel,
                parent: ViewGroup
            ): SpecOrdersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SpecialtyOrderItemBinding
                    .inflate(layoutInflater, parent, false)
                return SpecOrdersViewHolder(viewModel, binding)
            }
        }
    }
}

class SpecOrderDiffCallback : DiffUtil.ItemCallback<SpecialtyOrder>() {

    override fun areItemsTheSame(oldItem: SpecialtyOrder, newItem: SpecialtyOrder): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: SpecialtyOrder, newItem: SpecialtyOrder): Boolean {
        return oldItem == newItem
    }
}