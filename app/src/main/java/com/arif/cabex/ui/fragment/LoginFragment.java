package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.databinding.FragmentLoginBinding;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {
	private FragmentLoginBinding binding;
	private NavDirections direction;
	private NavController navController;

	@Override
	public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentLoginBinding.inflate(inflater, container, false);
		direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment("9836");
		binding.registrationLink.setOnClickListener(this::onRegistrationLinkClicked);
		binding.signIn.setOnClickListener(this::onSignInButtonClicked);
		binding.googleButton.setOnClickListener(this::onGoogleButtonClicked);

		return binding.getRoot();
	}

	private void onRegistrationLinkClicked(View view) {
		NavHostFragment.findNavController(this).navigate(direction);
	}

	private void onSignInButtonClicked(View view) {
		NavHostFragment.findNavController(this).navigate(direction);
	}

	private void onGoogleButtonClicked(View view) {
		NavHostFragment.findNavController(this).navigate(direction);
	}

}