package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cabex.databinding.FragmentLoginBinding;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment implements View.OnClickListener {
	private FragmentLoginBinding binding;
	private NavDirections direction;
	private NavController navController;

	@Override
	public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentLoginBinding.inflate(inflater, container, false);
		direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
		binding.registrationLink.setOnClickListener(this);
		binding.signInButton.setOnClickListener(this);
		binding.googleButton.setOnClickListener(this);

		return binding.getRoot();
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if(id == binding.registrationLink.getId()){
			onRegistrationLinkClicked(view);
		}
		else if(id == binding.signInButton.getId()){
			onSignInButtonClicked(view);
		}
		else if(id == binding.googleButton.getId()){
			onGoogleButtonClicked(view);
		}
	}

	private void onRegistrationLinkClicked(View view) {
	}


	private void onSignInButtonClicked(View view) {
		if(true){
			navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment().getActionId());


		}
	}

	private void onGoogleButtonClicked(View view) {
	}

}