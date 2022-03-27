package com.example.belttools.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.belttools.R
import com.example.belttools.databinding.FragmentDepartmentNotesBinding
import com.example.belttools.ui.viewmodel.SharedViewModel

class DepartmentNotesFragment : Fragment() {

    private var binding: FragmentDepartmentNotesBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentDepartmentNotesBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        setUpAppBar()

        // todo: when deleting a note
        sharedViewModel.deleteNotes(sharedViewModel.departmentToEdit!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.departmentToEdit = null
    }

    // SET UP //
    private fun setUpAppBar() {
        binding?.apply {
            // todo: "$departmentName Notes"
            topAppbar.title = "Department Notes"
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
}