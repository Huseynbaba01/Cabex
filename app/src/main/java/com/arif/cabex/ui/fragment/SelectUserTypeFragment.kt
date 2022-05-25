package com.arif.cabex.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arif.cabex.R
import com.arif.cabex.databinding.FragmentSelectUserTypeBinding
import java.security.PrivateKey

class SelectUserTypeFragment : Fragment() {
    private lateinit var binding: FragmentSelectUserTypeBinding
    lateinit var sharedPreferences:SharedPreferences
    lateinit var myEdit:SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSelectUserTypeBinding.inflate(inflater, container, false)
        sharedPreferences =requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        myEdit = sharedPreferences.edit()
        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        binding.driver.setOnClickListener{
            myEdit.putBoolean("isDriver",true)
            myEdit.apply()
            findNavController().navigate(SelectUserTypeFragmentDirections.actionSelectUserTypeFragmentToSelectSigningTypeFragment())
        }

        binding.passenger.setOnClickListener{
            myEdit.putBoolean("isDriver",false)
            myEdit.apply()
            findNavController().navigate(SelectUserTypeFragmentDirections.actionSelectUserTypeFragmentToSelectSigningTypeFragment())
        }
    }
}