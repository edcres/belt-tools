package com.example.belttools.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belttools.databinding.FragmentDepartmentExtensionBinding
import com.example.belttools.databinding.FragmentStartBinding
import com.example.belttools.ui.adapters.DeptExtensionsAdapter
import com.example.belttools.ui.viewmodel.SharedViewModel

class DepartmentExtensionFragment : Fragment() {

    private var binding: FragmentDepartmentExtensionBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var deptExtensionsAdapter: DeptExtensionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentDepartmentExtensionBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        deptExtensionsAdapter = DeptExtensionsAdapter(sharedViewModel)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            extensionsRecycler.adapter = deptExtensionsAdapter
            extensionsRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
        setUpAppBar()
    }

    // SET UP //
    private fun setUpAppBar() {
        binding?.apply {
            topAppbar.title = "Extensions"
            topAppbar.setNavigationOnClickListener {
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                navController.navigateUp()
            }
        }
    }
}