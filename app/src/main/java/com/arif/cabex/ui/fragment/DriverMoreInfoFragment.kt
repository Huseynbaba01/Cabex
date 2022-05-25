package com.arif.cabex.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.arif.cabex.databinding.FragmentDriverInfoBinding
import com.arif.cabex.event.ChangeNavbarVisibilityEvent
import com.arif.cabex.model.DriverInfo
import com.arif.cabex.ui.dialog.ConfirmOrderDialog
import org.greenrobot.eventbus.EventBus


class DriverMoreInfoFragment : Fragment() {
   private lateinit var binding: FragmentDriverInfoBinding
   private val args by navArgs<DriverMoreInfoFragmentArgs>()
    private lateinit var user: DriverInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBus.getDefault().post(ChangeNavbarVisibilityEvent(false))

        binding = FragmentDriverInfoBinding.inflate(inflater,container,false)
        user = MainFragment.listOfDriversInfo[args.position]
        binding.call.setOnClickListener{
//            callingSomeone()
        }
        binding.contactNumber.setOnClickListener{
            openWhatsapp("+994503432260")
        }
        binding.order.setOnClickListener {
            val dialog = ConfirmOrderDialog(requireContext())
            dialog.show()
            dialog.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
        }
        binding.driverName.text = user.driverFullName
        binding.imageView.setBackgroundResource(user.profilePicture!!)
        binding.departureTime.text = user.departureTime
        binding.carBrand.text = user.carBrand
        binding.emptySeats.text = user.seats
        if(user.carPlate == null)
            binding.carPlate.text = "hidden"
        else binding.carPlate.text = user.carPlate
        if(user.contactNumber == null)
            binding.contactNumber.text = "hidden"
        else binding.contactNumber.text = user.contactNumber
        if(user.carColor == null)
            binding.carColor.text = "hidden"
        else binding.carColor.text = user.carColor

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