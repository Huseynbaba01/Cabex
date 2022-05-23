package com.arif.cabex.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.arif.cabex.R
import com.arif.cabex.databinding.DialogConfirmOrderBinding

class ConfirmOrderDialog(val mContext: Context):Dialog(mContext) {
    private lateinit var binding: DialogConfirmOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogConfirmOrderBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setCancelable(false)
        startTimer()
        binding.closeInConfirmOrderFragment.setOnClickListener{
            dismiss()
        }
        binding.declineOrderInConfirmOrderFragment.setOnClickListener {
            Toast.makeText(mContext, "Order Declined!", Toast.LENGTH_LONG).show()
            dismiss()
        }
    }
    private fun startTimer() {
        object : CountDownTimer(20000, 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val secondUntilFinished=millisUntilFinished/1000
                if(secondUntilFinished<10){
                    binding.orderCountdown.text = "00:0$secondUntilFinished"
                }else{
                    binding.orderCountdown.text = "00:$secondUntilFinished"
                }

            }

            override fun onFinish() {
                Toast.makeText(mContext,"Your time is off! Order Confirmed",Toast.LENGTH_LONG).show()
                dismiss()
            }
        }.start()
    }
}