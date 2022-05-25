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
        return DriverInfoViewHolder(binding);
    }

    override fun onBindViewHolder(holder: DriverInfoViewHolder, position: Int) {
        val item = listOfDriverInfo[position]
        holder.profilePicture.setBackgroundResource(item.profilePicture!!)
        holder.route.text = item.driverRoute
        holder.fullName.text = item.driverFullName
        holder.departure.text = item.departurePositionTime
        holder.carBrand.text = item.carBrand
        if (item.isOpened) {
            holder.recyclerViewLine2.visibility = View.VISIBLE
            holder.arrowDown.rotation = 180f
        } else {
            holder.recyclerViewLine2.visibility = View.GONE
            holder.arrowDown.rotation = 0f
        }
        holder.recyclerViewLine1.setOnClickListener {
            if (item.isOpened) {
                item.isOpened = false
                holder.recyclerViewLine2.visibility = View.GONE
                holder.arrowDown.rotation = 0f
            } else {
                item.isOpened = true
                holder.recyclerViewLine2.visibility = View.VISIBLE
                holder.arrowDown.rotation = 180f
            }
        }

        holder.seats.text = item.seats
    }

    override fun getItemCount(): Int {
        return listOfDriverInfo.size
    }
}