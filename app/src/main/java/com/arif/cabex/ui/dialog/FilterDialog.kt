package com.arif.cabex.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.arif.cabex.databinding.DialogFilterBinding

class FilterDialog(context: Context): Dialog(context) {
    private lateinit var binding: DialogFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.close.setOnClickListener {
            dismiss()
        }
        window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
    }
}