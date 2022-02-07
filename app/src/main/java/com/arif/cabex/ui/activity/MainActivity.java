package com.arif.cabex.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.arif.cabex.R;
import com.arif.cabex.databinding.ActivityMainBinding;
import com.arif.cabex.helper.GenericTextWatcher;

public class MainActivity extends AppCompatActivity {
	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());

		binding.firstOtp.addTextChangedListener(new GenericTextWatcher(binding.secondOtp, binding.firstOtp));
		binding.secondOtp.addTextChangedListener(new GenericTextWatcher(binding.thirdOtp, binding.firstOtp));
		binding.thirdOtp.addTextChangedListener(new GenericTextWatcher(binding.fourthOtp, binding.secondOtp));
		binding.fourthOtp.addTextChangedListener(new GenericTextWatcher(binding.fourthOtp, binding.thirdOtp));
		setContentView(binding.getRoot());
	}
}