package com.example.belttools.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.belttools.R
import com.example.belttools.data.model.entities.Department
import com.example.belttools.databinding.FragmentDepartmentExtensionsBinding
import com.example.belttools.ui.adapters.DeptExtensionsAdapter
import com.example.belttools.ui.viewmodel.SharedViewModel
import com.example.belttools.util.filterExtensionNotNull

class DepartmentExtensionsFragment : Fragment() {

    private var binding: FragmentDepartmentExtensionsBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var deptExtensionsAdapter: DeptExtensionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentDepartmentExtensionsBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        deptExtensionsAdapter = DeptExtensionsAdapter(requireContext(), sharedViewModel, viewLifecycleOwner)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            extensionsRecycler.adapter = deptExtensionsAdapter
            extensionsRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
            addDepartmentBtn.setOnClickListener {
                sharedViewModel.insertDepartment(Department())
            }
        }
        setUpAppBar()
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // SET UP //
    private fun setObservers() {
        sharedViewModel.departments.observe(viewLifecycleOwner) {
            deptExtensionsAdapter.submitList(filterExtensionNotNull(it))
        }
    }
    private fun setUpAppBar() {
        binding?.apply {
            topAppbar.title = "Extensions"
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
    // SET UP //
}