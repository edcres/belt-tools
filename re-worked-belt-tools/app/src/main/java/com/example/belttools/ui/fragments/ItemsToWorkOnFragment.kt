package com.example.belttools.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
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
        }
        setUpAppBar()
    }

    // SET UP //
    private fun setUpAppBar() {
        binding?.apply {
            // todo: either "Skus To Work On" or "Pallets To Work On"
            topAppbar.title = "Work On"
            topAppbar.setNavigationOnClickListener {
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                navController.navigateUp()
            }
        }
    }
}