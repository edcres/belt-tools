package com.example.belttools.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.belttools.R
import com.example.belttools.databinding.FragmentDepartmentNotesBinding
import com.example.belttools.ui.viewmodel.SharedViewModel
import com.example.belttools.util.displayToast
import com.example.belttools.util.getDepartmentNames

class DepartmentNotesFragment : Fragment() {

    private val fragmentTAG = "DeptNotesFrag_TAG"
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

            saveBtn.setOnClickListener {
                val deptToEdit = sharedViewModel.departmentToEdit
                if (deptToEdit != null) {
                    deptToEdit.notes = deptNotesTxt.text.toString()
                    sharedViewModel.updateDepartment(deptToEdit)
                } else {
                    displayToast(requireContext(), "Choose a department")
                }
            }
            deleteBtn.setOnClickListener {
                val deptToEdit = sharedViewModel.departmentToEdit
                if (deptToEdit != null) {
                    sharedViewModel.deleteNotes(deptToEdit)
                } else {
                    displayToast(requireContext(), "Choose a department")
                }
            }
        }
        setUpAppBar()
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.departmentToEdit = null
    }

    // CLICK LISTENERS //
    private fun spinnerOnClick() {
        binding?.apply {
            chooseDeptSpinner.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getDepartmentNames(sharedViewModel.departments.value!!.toList())
            )
            chooseDeptSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val departmentsList = sharedViewModel.departments.value!!
                    val departmentChosen = departmentsList[position]
                    sharedViewModel.departmentToEdit = departmentChosen
                    deptNotesTxt.setText(departmentChosen.notes)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.i(fragmentTAG, "Nothing selected.")
                }
            }
        }
    }

    // SET UP //
    private fun setObservers() {
        sharedViewModel.menuEditIsOn.observe(viewLifecycleOwner) { isOn ->
            binding?.apply {
                if (isOn) {
                    saveBtn.visibility = View.GONE
                    deleteBtn.visibility = View.VISIBLE
                } else {
                    deleteBtn.visibility = View.GONE
                    saveBtn.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun setUpAppBar() {
        binding?.apply {
            topAppbar.title = "Notes"
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