package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentOTPBinding;
import com.arif.cabex.helper.GenericTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

public class OTPFragment extends Fragment {
	private FragmentOTPBinding binding;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentOTPBinding.inflate(inflater, container, false);

		addTextChangedListeners();

		return binding.getRoot();
	}

	private void addTextChangedListeners(){
		binding.firstOtp.addTextChangedListener(new GenericTextWatcher(binding.firstOtp, binding.secondOtp, binding.firstOtp));
		binding.secondOtp.addTextChangedListener(new GenericTextWatcher(binding.secondOtp, binding.thirdOtp, binding.firstOtp));
		binding.thirdOtp.addTextChangedListener(new GenericTextWatcher(binding.thirdOtp, binding.fourthOtp, binding.secondOtp));
		binding.fourthOtp.addTextChangedListener(new GenericTextWatcher(binding.fourthOtp, binding.fifthOtp, binding.thirdOtp));
		binding.fifthOtp.addTextChangedListener(new GenericTextWatcher(binding.fifthOtp, binding.sixthOtp, binding.fourthOtp));
		binding.sixthOtp.addTextChangedListener(new GenericTextWatcher(binding.sixthOtp, binding.sixthOtp, binding.fifthOtp));
	}
}