package com.arif.cabex.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arif.cabex.model.DriverInfo
import com.arif.cabex.R
import com.arif.cabex.databinding.FragmentMainBinding
import com.arif.cabex.ui.adapter.DriverInfoAdapter
import com.arif.cabex.ui.dialog.FilterDialog

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    var listOfDriversInfo = ArrayList<DriverInfo>()
    var adapter: DriverInfoAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        setListeners()
        listOfDriversInfo.add(
            DriverInfo(R.drawable.filter_alt, "Bakı", "Gəncə", "Kərim Əhmədli",
                "14:00", "Ford Mustang",4,3)
        )
        listOfDriversInfo.add(
            DriverInfo(R.drawable.filter_alt, "Sumqayıt", "Qax", "Ələsgər Əliyev",
                "11:30", "Ferrari Diablo", 2,1))
        adapter = DriverInfoAdapter(listOfDriversInfo)


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

