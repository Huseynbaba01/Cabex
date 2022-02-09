package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cabex.MVVM.InstanceViewModel;
import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
	private FragmentRegisterBinding binding;
	InstanceViewModel viewModel=new InstanceViewModel();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentRegisterBinding.inflate(inflater, container, false);
		binding.signUp.setOnClickListener(this::onRegister);
		return binding.getRoot();
	}

	private void onRegister(View view) {
		String userName=binding.inputNumber.getText().toString();
		String password=binding.inputPassword.getText().toString();
		if(userName.isEmpty()){
			binding.inputNumber.setBackgroundColor(getResources().getColor(R.color.dark_red));
			return;
		}
		if(password.isEmpty()){
			binding.inputPassword.setBackgroundColor(getResources().getColor(R.color.dark_red));
			return;
		}
		viewModel.addAccountInformations(userName,password);

	}
}