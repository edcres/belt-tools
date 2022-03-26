package com.example.belttools.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.belttools.data.model.entities.Department
import com.example.belttools.databinding.DeptExtensionItemBinding
import com.example.belttools.ui.viewmodel.SharedViewModel

class DeptExtensionsAdapter(
    private val viewModel: SharedViewModel
): ListAdapter<Department, DeptExtensionsAdapter.DeptExtensionsViewHolder>(ExtensionsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DeptExtensionsViewHolder.from(viewModel, parent)

    override fun onBindViewHolder(holder: DeptExtensionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DeptExtensionsViewHolder private constructor(
        private val viewModel: SharedViewModel,
        private val binding: DeptExtensionItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(department: Department) {
            binding.apply {
                departmentEt.setText(department.name)
                extensionEt.setText(department.extensions)

                saveBtn.setOnClickListener {
                    department.name = departmentEt.text.toString()
                    department.extensions = extensionEt.text.toString()
                    viewModel.updateExtensions(department)
                }
            }
        }

        companion object {
            fun from(
                viewModel: SharedViewModel,
                parent: ViewGroup
            ): DeptExtensionsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DeptExtensionItemBinding
                    .inflate(layoutInflater, parent, false)
                return DeptExtensionsViewHolder(viewModel, binding)
            }
        }
    }
}

class ExtensionsDiffCallback : DiffUtil.ItemCallback<Department>() {
    override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
        return oldItem == newItem
    }
}