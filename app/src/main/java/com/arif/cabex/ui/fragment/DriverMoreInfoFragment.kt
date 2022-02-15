package com.arif.cabex.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arif.cabex.databinding.FragmentDriverProfileBinding


class DriverMoreInfoFragment : Fragment() {
   private lateinit var binding: FragmentDriverProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDriverProfileBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        fun callingSomeone() {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("+994503432260")//TODO replace with the driver's phone number
            startActivity(dialIntent)
        }
        binding.call.setOnClickListener{
            callingSomeone()
        }
        binding.contactNumber.setOnClickListener{
            openWhatsapp()
        }
        return binding.root
    }

    private fun openWhatsapp() {
        val url = "https://wa.me/+9111111111/?text="
        val whatsappIntent = Intent(Intent.ACTION_VIEW)
        whatsappIntent.data = Uri.parse(url)
        startActivity(whatsappIntent)
    }


}