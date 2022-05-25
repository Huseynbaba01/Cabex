package com.arif.cabex.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arif.cabex.databinding.FragmentEditPassengerProfileBinding
import com.arif.cabex.ui.activity.MainActivity

class EditProfileFragment: Fragment() {
    private lateinit var binding: FragmentEditPassengerProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPassengerProfileBinding.inflate(inflater, container, false)
        binding.lblLeave.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
        return binding.root
    }
}