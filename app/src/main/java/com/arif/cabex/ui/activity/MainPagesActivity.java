package com.arif.cabex.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.arif.cabex.databinding.ActivityMainPagesBinding;

public class MainPagesActivity extends AppCompatActivity {
	private ActivityMainPagesBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainPagesBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		binding.bottomNavigationView.setBackgroundColor(getColor(android.R.color.transparent));
	}
}