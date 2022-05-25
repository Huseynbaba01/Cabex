package com.arif.cabex.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arif.cabex.model.DriverInfo
import com.arif.cabex.R
import com.arif.cabex.databinding.FragmentMainBinding
import com.arif.cabex.ui.adapter.DriverInfoAdapter
import com.arif.cabex.ui.dialog.FilterDialog

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    var listOfDriversInfo = ArrayList<DriverInfo>()
    var adapter: DriverInfoAdapter? = null
    /*private val myList = ArrayList<DriverInfo>()*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*myList.add(DriverInfo(R.drawable.add_icon, ))*/
        binding = FragmentMainBinding.inflate(inflater)
        setListeners()
        listOfDriversInfo = ArrayList()
        listOfDriversInfo.add(
            DriverInfo(R.drawable.driver1, "Bakı", "Gəncə", "Kərim Əhmədli",
                "14:00", "Ford Mustang",4,3)
        )
        listOfDriversInfo.add(
            DriverInfo(R.drawable.driver2, "Sumqayıt", "Qax", "Ələsgər Əliyev",
                "11:30", "Ferrari Diablo", 2,1))
        listOfDriversInfo.add(DriverInfo(R.drawable.driver3,"Şəmkir","Qazax","Akif Rahmanov","03:25","Toyota Prius",4,2))
        listOfDriversInfo.add(DriverInfo(R.drawable.driver5,"Quba","Lənkəran","Südabə Əhmədova","13:30","Mini Cooper",4,3))
        listOfDriversInfo.add(DriverInfo(R.drawable.driver4,"Göygöl","Şuşa","Ülvi İsmayılov","10:00","Toyota Camry",4,4))
        adapter = DriverInfoAdapter(listOfDriversInfo)
        binding.items.adapter = adapter
        binding.items.layoutManager = LinearLayoutManager(requireContext())

        // Inflate the layout for this fragment
        return binding.root
        //load drivers
    }

    private fun setListeners(){
        binding.cvImage.setOnClickListener {
            val dialog = FilterDialog(requireContext())
            dialog.show()
        }
    }

    private fun addMockDrivers(){

    }
    companion object {

    }

    private fun details() {

    }
}

