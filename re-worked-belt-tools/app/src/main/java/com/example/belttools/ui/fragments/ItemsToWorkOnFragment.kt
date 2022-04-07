package com.example.belttools.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belttools.R
import com.example.belttools.databinding.FragmentItemsToWorkOnBinding
import com.example.belttools.ui.adapters.ItemsToWorkAdapter
import com.example.belttools.ui.viewmodel.SharedViewModel

class ItemsToWorkOnFragment : Fragment() {

    private var binding: FragmentItemsToWorkOnBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var itemsToWorkAdapter: ItemsToWorkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentItemsToWorkOnBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        itemsToWorkAdapter = ItemsToWorkAdapter(requireContext(), sharedViewModel, viewLifecycleOwner)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            itemsRecycler.adapter = itemsToWorkAdapter
            itemsRecycler.layoutManager = LinearLayoutManager(requireContext())
            addItemBtn.setOnClickListener {
                showAddItemWidget()
            }
            saveItemBtn.setOnClickListener {
                val newItemSKU = newItemEt.text.toString().toLong()
                if(!sharedViewModel.showNewSKU(newItemSKU)) {
                    // SKU is either added or updated.
                    sharedViewModel.updateSKUType(newItemSKU)
                }
                hideAddItemWidget()
            }
        }
        setUpAppBar()
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        sharedViewModel.itemsListToDisplay = ""
    }

    // SET UP //
    private fun setObservers() {
        sharedViewModel.skus.observe(viewLifecycleOwner) {
            sharedViewModel.getFilteredItemsList().observe(viewLifecycleOwner) { filteredSKUs ->
                itemsToWorkAdapter.submitList(filteredSKUs)
            }
        }
    }
    private fun setUpAppBar() {
        binding?.apply {
            topAppbar.title = sharedViewModel.itemsListToDisplay
            topAppbar.setNavigationOnClickListener {
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                navController.navigateUp()
            }
            topAppbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_toolbar_btn -> {
                        sharedViewModel.toggleEditBtn()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    // HELPERS //
    private fun showAddItemWidget() {
        binding?.apply {
            itemsRecycler.visibility = View.INVISIBLE
            addItemWidget.visibility = View.VISIBLE
        }
    }
    private fun hideAddItemWidget() {
        binding?.apply {
            itemsRecycler.visibility = View.VISIBLE
            addItemWidget.visibility = View.GONE
        }
    }
}