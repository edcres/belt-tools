package com.example.belttools.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belttools.data.model.entities.SpecialtyOrder
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
    ): View {
        val fragmentBinding = FragmentSpecialtyOrdersBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        specOrdersAdapter = SpecOrdersAdapter(this)
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
        sharedViewModel.specOrders.observe(viewLifecycleOwner) {
            specOrdersAdapter.submitList(it)
        }
        populateStoreNumber()
    }

    override fun onItemClick(position: Int) {
        // For the recycler item click listener.
        val specOrder = sharedViewModel.specOrders.value!![position]
        binding?.apply {
            orderNumEt.setText(specOrder.orderNum)
            orderInfoEt.setText(specOrder.info)
            orderNoteEt.setText(specOrder.note)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // CLICK HANDLERS //
    private fun deleteOrder() {
        val specOrder = findOrder(
            binding!!.orderNumEt.text.toString(),
            sharedViewModel.specOrders.value!!
        )
        if (specOrder != null) {
            sharedViewModel.removeSpecOrder(specOrder)
            clearViews()
        } else displayToast(requireContext(), "Order not found.")
    }

    private fun lookUpOrder() {
        binding?.apply {
            val specOrder = findOrder(
                orderNumEt.text.toString(),
                sharedViewModel.specOrders.value!!
            )
            if (specOrder != null) {
                orderNumEt.setText(specOrder.orderNum)
                orderInfoEt.setText(specOrder.info)
                orderNoteEt.setText(specOrder.note)
            } else displayToast(requireContext(), "Order not found.")
        }
    }

    private fun saveOrder() {
        val specOrder = findOrder(
            binding!!.orderNumEt.text.toString(),
            sharedViewModel.specOrders.value!!
        )
        binding?.apply {
            if (specOrder != null) {
                // Update order
                specOrder.info = orderInfoEt.text.toString()
                specOrder.note = orderNoteEt.text.toString()
                sharedViewModel.updateSpecOrder(specOrder)
            } else {
                // Insert new order
                sharedViewModel.insertSpecOrder(
                    SpecialtyOrder(
                        orderNum = orderNumEt.text.toString(),
                        info = orderInfoEt.text.toString(),
                        note = orderNoteEt.text.toString()
                    )
                )
            }
        }
        displayToast(requireContext(), "Order Saved")
        clearViews()
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

    // HELPERS //
    private fun clearViews() {
        binding?.apply {
            orderNumEt.text.clear()
            orderInfoEt.text.clear()
            orderNoteEt.text.clear()
        }
        populateStoreNumber()
    }

    private fun populateStoreNumber() {
        if (sharedViewModel.storeNumber != null) {
            val storeNumberDisplay = "${sharedViewModel.storeNumber}-"
            binding!!.orderNumEt.setText(storeNumberDisplay)
        }
    }
    // HELPERS //
}