package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentLoginBinding;
import com.arif.cabex.network.MyFirebase;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {
	private FragmentLoginBinding binding;
	private NavDirections direction;
	private boolean isEmailSection;
	private MyFirebase myFirebase = new MyFirebase();

	@Override
	public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentLoginBinding.inflate(inflater, container, false);
		direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
		isEmailSection = LoginFragmentArgs.fromBundle(getArguments()).getIsEmailSection();

		addHints();
		setActions();
		return binding.getRoot();
	}

	private void addHints() {
		binding.layoutPassword.setStartIconDrawable(R.drawable.ic_baseline_lock_24);
		if(isEmailSection){
			binding.mainIcon.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.ic_email));
			binding.countryCodePicker.setVisibility(View.GONE);
			binding.userName.setInputType(InputType.TYPE_CLASS_TEXT);
			binding.userName.setHint("E-poçt");
		}else{
			binding.userName.setInputType(InputType.TYPE_CLASS_NUMBER);
			binding.userName.setHint("Telefon nömrəsi");
			binding.mainIcon.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.ic_baseline_phone_android_24));

		}
	}


	private void setActions() {
		binding.layoutPassword.setPasswordVisibilityToggleEnabled(true);
		binding.registrationLink.setOnClickListener(this::onRegistrationLinkClicked);
		binding.signIn.setOnClickListener(this::onSignInButtonClicked);
		binding.googleButton.setOnClickListener(this::onGoogleButtonClicked);

		binding.forgetPassword.setOnClickListener(this::moveToForgetPasswordFragment);

		binding.inputPassword.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				binding.layoutPassword.setPasswordVisibilityToggleEnabled(true);
			}
		});
	}

	private void moveToForgetPasswordFragment(View view) {
		NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.actionLoginFragmentToForgetPassword2());
	}

	private void onRegistrationLinkClicked(View view) {
		NavHostFragment.findNavController(this).navigate(direction);
	}

	private void onSignInButtonClicked(View view) {
		if(binding.userName.getText().toString().isEmpty()){
			if(isEmailSection)
				Toast.makeText(getActivity(), "E-poçt boş ola bilməz, yazıb yenidən yoxlayın!", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getActivity(), "Telefon nömrəsi boş ola bilməz, yazıb yenidən yoxlayın!", Toast.LENGTH_SHORT).show();

			return;
		}
		if(binding.inputPassword.getText().toString().equals("")) {
			binding.layoutPassword.setPasswordVisibilityToggleEnabled(false);
			binding.inputPassword.setError("Şifrə boş ola biməz!");
			return;
		}

		if(!isEmailSection)
			myFirebase.signWithPhoneNumber(binding.countryCodePicker.getSelectedCountryCode()+binding.userName.getText().toString(),binding.inputPassword.getText().toString(),requireContext());
		else
			myFirebase.signInWithEmail(binding.userName.getText().toString(),binding.inputPassword.getText().toString());
	}

	private void onGoogleButtonClicked(View view) {
		NavHostFragment.findNavController(this).navigate(direction);
	}



}