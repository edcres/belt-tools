package com.example.belttools.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belttools.databinding.FragmentSpecialtyOrdersBinding
import com.example.belttools.ui.adapters.SpecOrdersAdapter
import com.example.belttools.ui.viewmodel.SharedViewModel

class SpecialtyOrdersFragment : Fragment(), SpecOrdersAdapter.OnItemClickListener {

    private var binding: FragmentSpecialtyOrdersBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var specOrdersAdapter: SpecOrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSpecialtyOrdersBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        specOrdersAdapter = SpecOrdersAdapter(sharedViewModel, this)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            ordersRecycler.adapter = specOrdersAdapter
            ordersRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
        setUpAppBar()
    }

    override fun onItemClick(position: Int) {
        // For the recycler item click listener.
        val specOrder = sharedViewModel.specOrders.value!![position]
        binding?.apply {
            orderNumTxt.setText(specOrder.orderNum)
            orderInfoEt.setText(specOrder.info)
            orderNoteEt.setText(specOrder.note)
        }
    }

    // SET UP //
    private fun setUpAppBar() {
        binding?.apply {
            topAppbar.title = "Specialty Orders"
            topAppbar.setNavigationOnClickListener {
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                navController.navigateUp()
            }
        }
    }
}