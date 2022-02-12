package com.arif.cabex.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.arif.cabex.DriverInfo
import com.arif.cabex.R
import com.arif.cabex.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    var listOfDriversInfo = ArrayList<DriverInfo>()
    var adapter:DriverInfoAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
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

    class DriverInfoAdapter: BaseAdapter {
        var listOfDriverInfo = ArrayList<DriverInfo>()
        constructor(listOfDriverInfo: ArrayList<DriverInfo>):super(){

        }
        override fun getCount(): Int {
            return listOfDriverInfo.size
        }

        override fun getItem(p0: Int): Any {
            return listOfDriverInfo[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            TODO("Not yet implemented")

        }
    }

    companion object {

    }

    private fun details() {

    }
}

