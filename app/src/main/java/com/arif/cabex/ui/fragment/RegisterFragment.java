package com.arif.cabex.ui.fragment;

import static com.arif.cabex.R.drawable.ic_baseline_phone_android_24;
import static com.arif.cabex.R.drawable.ic_email;

import android.graphics.PorterDuff;
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
	boolean isEmailSelected = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentRegisterBinding.inflate(inflater, container, false);
		binding.signUp.setOnClickListener(this::onRegister);
		binding.sectionPhone.setOnClickListener(this::onSectionPhoneClicked);
		binding.sectionEmail.setOnClickListener(this::onSectionEmailClicked);
		binding.passwordLayout.setStartIconDrawable(R.drawable.ic_baseline_lock_24);
		binding.userNameLayout.setStartIconDrawable(ic_email);
		return binding.getRoot();
	}

	private void onSectionEmailClicked(View view) {
		if(!isEmailSelected){
			isEmailSelected=true;
			binding.userNameLayout.setStartIconDrawable(ic_email);
			binding.userNameLayout.setHint("Email");
			binding.cardEmail.setVisibility(View.VISIBLE);
			binding.cardPhoneNumber.setVisibility(View.GONE);
		}
	}

	private void onSectionPhoneClicked(View view) {
		if(isEmailSelected){
			isEmailSelected=false;
			binding.userNameLayout.setStartIconDrawable(ic_baseline_phone_android_24);
			binding.userNameLayout.setHint("Phone number");
			binding.cardPhoneNumber.setVisibility(View.VISIBLE);
			binding.cardEmail.setVisibility(View.GONE);
		}
	}

	private void onRegister(View view) {
		String userName=binding.editUserName.getText().toString();
		String password=binding.editPassword.getText().toString();
		if(userName.isEmpty()){
			binding.editUserName.setError("Email can't be empty!");
			return;
		}
		if(password.isEmpty()){
			binding.editPassword.setBackgroundTintMode(PorterDuff.Mode.LIGHTEN);
			return;
		}
		viewModel.addAccountInformations(userName,password);

	}
}