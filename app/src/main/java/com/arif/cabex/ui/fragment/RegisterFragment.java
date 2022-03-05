package com.arif.cabex.ui.fragment;

import static com.arif.cabex.R.drawable.ic_baseline_phone_android_24;
import static com.arif.cabex.R.drawable.ic_email;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.MVVM.RegisterViewModel;
import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
	private String TAG  = "RegisterFragment";

	private FragmentRegisterBinding binding;
	private RegisterViewModel viewModel=new RegisterViewModel();
	private boolean isEmailSelected = true;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentRegisterBinding.inflate(inflater, container, false);
		startUI();
		return binding.getRoot();
	}

	private void startUI(){
		binding.passwordLayout.setStartIconDrawable(R.drawable.ic_baseline_lock_24);
		binding.userNameLayout.setStartIconDrawable(ic_email);

		setListeners();
	}

	private void setListeners(){
		binding.sectionPhone.setOnClickListener(this::onSectionPhoneClicked);
		binding.sectionEmail.setOnClickListener(this::onSectionEmailClicked);
		binding.signUp.setOnClickListener(this::onRegister);
	}


	private void onSectionEmailClicked(View view) {
		if(!isEmailSelected){
			isEmailSelected=true;
			binding.userNameLayout.setStartIconDrawable(ic_email);
			binding.userNameLayout.setHint("Email");
			binding.cardEmail.setVisibility(View.VISIBLE);
			binding.cardPhoneNumber.setVisibility(View.GONE);
			binding.countryCodePicker.setVisibility(View.GONE);
		}
	}

	private void onSectionPhoneClicked(View view) {
		if(isEmailSelected){
			isEmailSelected=false;
			binding.userNameLayout.setStartIconDrawable(ic_baseline_phone_android_24);
			binding.userNameLayout.setHint("Phone number");
			binding.cardPhoneNumber.setVisibility(View.VISIBLE);
			binding.cardEmail.setVisibility(View.GONE);
			binding.countryCodePicker.setVisibility(View.VISIBLE);
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
			binding.editPassword.setError("Password can't be empty!");
		}
		boolean isRegistered=viewModel.onVerification(binding
				,userName
				,password
				,binding.countryCodePicker.getSelectedCountryCode()
				,isEmailSelected
				,requireActivity());

		searchVerification(isRegistered);
	}

	private void searchVerification(boolean isRegistered) {
		if(isRegistered){
			Toast.makeText(requireActivity(), "You succesfully registered.", Toast.LENGTH_SHORT).show();
			if(!isEmailSelected)
				NavHostFragment
						.findNavController(this)
						.navigate(RegisterFragmentDirections
								.actionRegisterFragmentToOTPFragment());
		}else{
			Toast.makeText(requireActivity(), "Your verification failed!", Toast.LENGTH_SHORT).show();
		}
	}

}