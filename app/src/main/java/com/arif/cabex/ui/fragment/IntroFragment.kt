package com.arif.cabex.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arif.cabex.databinding.FragmentIntroBinding


class IntroFragment : Fragment() {
    lateinit var binding:FragmentIntroBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentIntroBinding.inflate(inflater)
        waitForAMoment()

        return binding.root
    }

    private fun waitForAMoment() {
        Handler(Looper.getMainLooper()).postDelayed(
                {
                    findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToSelectUserTypeFragment())
                },2000
        )
    }

}