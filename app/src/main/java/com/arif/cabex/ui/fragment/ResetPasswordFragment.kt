package com.arif.cabex.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.arif.cabex.databinding.FragmentResetPasswordBinding
import com.arif.cabex.network.MyFirebase

class ResetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater)
        setClickListeners()
        return binding.root
    }

    private fun setClickListeners() {
        binding.goBackBtn.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.repeatPasswordInput.addTextChangedListener{
            binding.repeatPasswordInputLayout.isPasswordVisibilityToggleEnabled = true
            binding.repeatPasswordInputLayout.isErrorEnabled = false
        }

        binding.resetPassword.setOnClickListener {
            if(binding.newPasswordInput.text.toString() == binding.repeatPasswordInput.text.toString()){
                Toast.makeText(requireContext(),"Şifrəniz yeniləndi.",Toast.LENGTH_LONG).show()
                findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
            }else if(binding.newPasswordInput.text.toString().isEmpty()){
                Toast.makeText(requireContext(),"Xanalar boş ola bilməz!",Toast.LENGTH_LONG).show()
                binding.newPasswordInput.setText("")
            }

            else{
                binding.repeatPasswordInputLayout.isPasswordVisibilityToggleEnabled = false
                binding.repeatPasswordInputLayout.isErrorEnabled = true
                binding.repeatPasswordInput.setText("")
                binding.repeatPasswordInputLayout.error = "Təkrar şifrə eyni olmalıdır!"
            }
        }
    }

}