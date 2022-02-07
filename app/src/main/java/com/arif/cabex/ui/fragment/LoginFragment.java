package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
	private FragmentLoginBinding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentLoginBinding.inflate(inflater, container, false);

		return binding.getRoot();
	}
}