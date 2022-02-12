package com.arif.cabex.ui.holder

import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.arif.cabex.databinding.MainPageListItemBinding

class MyViewHolder(binding: MainPageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    var recylerViewLine1 = binding.rlRecylerViewLine1
    var recyclerViewLine2 = binding.rlMPLILine2
    var profilePicture : ImageView = binding.profilePicture
    var arrowDown : ImageView = binding.ivArrowDown
    var route : TextView = binding.tcRoute
    var fullName : TextView = binding.tvFullName
    var departure : TextView = binding.tvDeparture
    var carBrand : TextView = binding.carBrand
    var seats : TextView = binding.seats


}