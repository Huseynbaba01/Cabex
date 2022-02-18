package com.arif.cabex.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.arif.cabex.R;
import com.arif.cabex.databinding.ActivityMainBinding;
import com.arif.cabex.databinding.ActivityMainPagesBinding;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
	private final String TAG = "MainActivity";
	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
	}
}