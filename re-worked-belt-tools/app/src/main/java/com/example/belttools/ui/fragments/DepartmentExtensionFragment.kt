package com.example.belttools.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.belttools.databinding.FragmentDepartmentExtensionBinding
import com.example.belttools.databinding.FragmentStartBinding
import com.example.belttools.ui.viewmodel.SharedViewModel

class DepartmentExtensionFragment : Fragment() {

    private var binding: FragmentDepartmentExtensionBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentDepartmentExtensionBinding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
}