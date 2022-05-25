package com.arif.cabex.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arif.cabex.R
import com.arif.cabex.databinding.FragmentSelectSigningTypeBinding

class SelectSigningTypeFragment : Fragment() {
    private lateinit var binding: FragmentSelectSigningTypeBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSelectSigningTypeBinding.inflate(inflater, container,false)
        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.btnCreate.setOnClickListener{
            findNavController().navigate(SelectSigningTypeFragmentDirections.actionSelectSigningTypeFragmentToRegisterFragment())
        }

        binding.btnIn.setOnClickListener{
            findNavController().navigate(SelectSigningTypeFragmentDirections.actionSelectSigningTypeFragmentToLoginFragment())
        }
    }
}