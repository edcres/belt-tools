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
import com.example.belttools.util.displayToast
import com.example.belttools.util.findOrder

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
            deleteOrderBtn.setOnClickListener { deleteOrder() }
            lookUpOrderBtn.setOnClickListener { lookUpOrder() }
            saveOrderBtn.setOnClickListener { saveOrder() }
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

    // CLICK HANDLERS //
    private fun deleteOrder() {
        val specOrder = findOrder(
            binding!!.orderNumTxt.text.toString(),
            sharedViewModel.specOrders.value!!.toList()
        )
        if (specOrder != null) {
            sharedViewModel.removeSpecOrder(specOrder)
        } else {
            displayToast(requireContext(), "Order not found.")
        }
    }
    private fun lookUpOrder() {
        // todo:
    }
    private fun saveOrder() {
        // todo:
    }
    // CLICK HANDLERS //

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