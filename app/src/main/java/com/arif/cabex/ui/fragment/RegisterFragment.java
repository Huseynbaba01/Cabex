package com.arif.cabex.ui.fragment;

import static com.arif.cabex.R.drawable.ic_baseline_phone_android_24;
import static com.arif.cabex.R.drawable.ic_email;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.MVVM.RegisterViewModel;
import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentRegisterBinding;
import com.arif.cabex.event.OTPSentEvent;
import com.arif.cabex.event.OTPVerifiedEvent;
import com.arif.cabex.event.getMainUserText;
import com.arif.cabex.event.getPassword;
import com.arif.cabex.network.firebase.MyFirebase;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

public class RegisterFragment extends BaseFragment {
	private String TAG  = "RegisterFragment";

	private FragmentRegisterBinding binding;
	private RegisterViewModel viewModel=new RegisterViewModel();
	private boolean isEmailSelected = true;
	NavDirections directions;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentRegisterBinding.inflate(inflater, container, false);
		directions=RegisterFragmentDirections.actionRegisterFragmentToOTPFragment();

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

		sendTextsToEventBus();

//		verifyWithReCaptcha();

		viewModel.onVerification(userName
				,password
				,binding.countryCodePicker.getSelectedCountryCode()
				,isEmailSelected
				,requireActivity(),
				requireContext());


	}

//	private void verifyWithReCaptcha() {
//			SafetyNet.getClient(requireContext()).verifyWithRecaptcha("60F9E78F-5A6C-4F03-92EB-48DCAE035D6B")
//					.addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
//								@Override
//								public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
//									// Indicates communication with reCAPTCHA service was
//									// successful.
//									String userResponseToken = response.getTokenResult();
//									if (!userResponseToken.isEmpty()) {
//										// Validate the user response token using the
//										// reCAPTCHA siteverify API.
//									}
//								}
//							})
//					.addOnFailureListener(new OnFailureListener() {
//						@Override
//						public void onFailure(@NonNull Exception e) {
//							if (e instanceof ApiException) {
//								// An error occurred when communicating with the
//								// reCAPTCHA service. Refer to the status code to
//								// handle the error appropriately.
//								ApiException apiException = (ApiException) e;
//								int statusCode = apiException.getStatusCode();
//								Log.d(TAG, "Error: " + CommonStatusCodes
//										.getStatusCodeString(statusCode));
//							} else {
//								// A different, unknown type of error occurred.
//								Log.d(TAG, "Error: " + e.getMessage());
//							}
//						}
//					});
//
//	}

	private void sendTextsToEventBus() {
		EventBus.getDefault().postSticky(new getMainUserText(binding.editUserName.getText().toString()));
		EventBus.getDefault().postSticky(new getPassword(binding.editPassword.getText().toString()));
	}


	@Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
	public void changeFragment(OTPVerifiedEvent otpVerifiedEvent) {
		Log.d(TAG, "changeFragment: Came here");
		NavHostFragment
				.findNavController(this)
				.navigate(RegisterFragmentDirections
						.actionRegisterFragmentToOTPFragment());
	}

}