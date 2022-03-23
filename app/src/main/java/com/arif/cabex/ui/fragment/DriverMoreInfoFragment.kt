package com.arif.cabex.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arif.cabex.databinding.FragmentDriverInfoBinding


class DriverMoreInfoFragment : Fragment() {
   private lateinit var binding: FragmentDriverInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDriverInfoBinding.inflate(inflater,container,false)

        binding.call.setOnClickListener{
            callingSomeone()
        }
        binding.contactNumber.setOnClickListener{
            openWhatsapp("+994503432260")
        }
        return binding.root
    }

    private fun openWhatsapp(number: String) {
        val url = "https://wa.me/$number/?text=Salam, necəsən, Hüseyn?"
        val whatsappIntent = Intent(Intent.ACTION_VIEW)
        whatsappIntent.data = Uri.parse(url)
        startActivity(whatsappIntent)
    }
    private fun callingSomeone() {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("+994503432260")//TODO replace with the driver's phone number
        startActivity(dialIntent)
    }

}