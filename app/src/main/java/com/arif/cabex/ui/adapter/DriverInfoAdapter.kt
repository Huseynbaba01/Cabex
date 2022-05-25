package com.arif.cabex.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arif.cabex.model.DriverInfo
import com.arif.cabex.databinding.MainPageListItemBinding
import com.arif.cabex.ui.holder.DriverInfoViewHolder

class DriverInfoAdapter(var listOfDriverInfo: ArrayList<DriverInfo>) :
    RecyclerView.Adapter<DriverInfoViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverInfoViewHolder {
        val binding = MainPageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriverInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DriverInfoViewHolder, position: Int) {
        holder.profilePicture.setBackgroundResource(listOfDriverInfo[position].profilePicture!!)
        holder.route.text = listOfDriverInfo[position].driverRoute
        holder.recyclerViewLine1.setOnClickListener {
            if (listOfDriverInfo[position].isOpened) {
                listOfDriverInfo[position].isOpened = false
                holder.recyclerViewLine2.visibility = View.GONE
                holder.arrowDown.rotation = 180f
            } else {
                listOfDriverInfo[position].isOpened = true
                holder.recyclerViewLine2.visibility = View.VISIBLE
                holder.arrowDown.rotation = 0f
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfDriverInfo.size
    }
}