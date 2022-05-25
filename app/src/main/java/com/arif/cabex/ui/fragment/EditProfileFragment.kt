package com.arif.cabex.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arif.cabex.databinding.FragmentEditPassengerProfileBinding
import com.arif.cabex.event.ChangeNavbarVisibilityEvent
import com.arif.cabex.network.MyFirebase
import com.arif.cabex.ui.activity.MainActivity
import org.greenrobot.eventbus.EventBus

class EditProfileFragment: Fragment() {
    private lateinit var binding: FragmentEditPassengerProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBus.getDefault().post(ChangeNavbarVisibilityEvent(true))
        binding = FragmentEditPassengerProfileBinding.inflate(inflater, container, false)
        binding.lblLeave.setOnClickListener {

            MyFirebase.getDefault().logOut()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
        return binding.root
    }
}