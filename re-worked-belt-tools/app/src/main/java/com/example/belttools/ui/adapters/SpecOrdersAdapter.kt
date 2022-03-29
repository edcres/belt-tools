package com.example.belttools.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.belttools.data.model.entities.SpecialtyOrder
import com.example.belttools.databinding.SpecialtyOrderItemBinding
import com.example.belttools.ui.viewmodel.SharedViewModel

class SpecOrdersAdapter(
    private val viewModel: SharedViewModel,
    private val onItemClickListener: OnItemClickListener
): ListAdapter<SpecialtyOrder, SpecOrdersAdapter.SpecOrdersViewHolder>(SpecOrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SpecOrdersViewHolder.from(viewModel, onItemClickListener, parent)

    override fun onBindViewHolder(holderWorkouts: SpecOrdersViewHolder, position: Int) =
        holderWorkouts.bind(getItem(position))

    class SpecOrdersViewHolder private constructor(
        private val viewModel: SharedViewModel,
        private val onItemClickListener: OnItemClickListener,
        private val binding: SpecialtyOrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.orderRecyclerItemContainer.setOnClickListener(this)
        }

        fun bind(specialtyOrder: SpecialtyOrder) {
            binding.apply {
                orderIdTxt.text = specialtyOrder.id.toString()
                orderNumberTxt.text = specialtyOrder.orderNum
                orderNoteTxt.text = specialtyOrder.note
            }
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(position)
            }
        }

        companion object {
            fun from(
                viewModel: SharedViewModel,
                onItemClickListener: OnItemClickListener,
                parent: ViewGroup
            ): SpecOrdersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SpecialtyOrderItemBinding
                    .inflate(layoutInflater, parent, false)
                return SpecOrdersViewHolder(viewModel, onItemClickListener, binding)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
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