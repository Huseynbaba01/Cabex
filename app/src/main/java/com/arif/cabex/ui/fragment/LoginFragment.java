package com.arif.cabex.ui.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.MVVM.InstanceViewModel;
import com.arif.cabex.databinding.FragmentLoginBinding;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {
	private FragmentLoginBinding binding;
	private NavDirections direction;
	InstanceViewModel viewModel;

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
		viewModel=new InstanceViewModel();
		viewModel.declareLiveData();
		if(binding.inputNumber.getText().toString().isEmpty()){
//			binding.inputNumber.setError("Number section can't be empty!");
			return;
		}
		if(binding.inputPassword.getText().toString().equals("")) {
			binding.inputPassword.setError("Password can't be empty!");
			return;
		}
		Toast.makeText(getContext(), "This is some of code!...", Toast.LENGTH_SHORT).show();
		viewModel.checkLogin(binding.inputNumber.toString(),binding.inputPassword.toString());
	}

	private void onGoogleButtonClicked(View view) {
		NavHostFragment.findNavController(this).navigate(direction);
	}



}