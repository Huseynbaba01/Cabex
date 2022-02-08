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
		setContentView(binding.getRoot());
	}
}