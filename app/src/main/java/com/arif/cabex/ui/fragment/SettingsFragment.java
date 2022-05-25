package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
	FragmentSettingsBinding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentSettingsBinding.inflate(inflater);
		return binding.getRoot();
	}
}