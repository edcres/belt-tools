package com.example.belttools.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belttools.data.model.entities.SKU
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
        itemsToWorkAdapter = ItemsToWorkAdapter(sharedViewModel)
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
                if(!sharedViewModel.showNewSKU(newItemEt.text.toString().toLong())) {
                    sharedViewModel.updateSKUType(newItemEt.text.toString().toLong())
                }

                // todo: observe change in SKUs and if 'itemsListToDisplay' == pallet of floor,
                //  displays skus accordingly.


                hideAddItemWidget()
            }
        }
        setUpAppBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.itemsListToDisplay = ""
    }

    // SET UP //
    private fun setUpAppBar() {
        binding?.apply {
            topAppbar.title = sharedViewModel.itemsListToDisplay
            topAppbar.setNavigationOnClickListener {
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                navController.navigateUp()
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