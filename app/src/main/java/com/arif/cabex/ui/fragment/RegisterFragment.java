package com.arif.cabex.ui.fragment;

import static com.arif.cabex.R.drawable.ic_baseline_phone_android_24;
import static com.arif.cabex.R.drawable.ic_email;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.MVVM.InstanceViewModel;
import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentRegisterBinding;
import com.arif.cabex.helper.CommonOperationHelper;
import com.arif.cabex.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {
	private String TAG  = "RegisterFragment";

	private FragmentRegisterBinding binding;
	private InstanceViewModel viewModel=new InstanceViewModel();
	private FirebaseAuth auth = FirebaseAuth.getInstance();
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
		binding.signUp.setOnClickListener(this::onRegister);
		binding.sectionPhone.setOnClickListener(this::onSectionPhoneClicked);
		binding.sectionEmail.setOnClickListener(this::onSectionEmailClicked);
		binding.signUp.setOnClickListener(this::onSignUpClicked);
	}

	private void onSignUpClicked(View view){
		if(isEmailSelected){
			registerWithEmail();
		}
		else{
			registerWithPhoneNumber();
			NavHostFragment
					.findNavController(this)
					.navigate(RegisterFragmentDirections
							.actionRegisterFragmentToOTPFragment());

		}
	}
	///todo
	/*"appAssociation": "AUTO",
			"rewrites": [ { "source": "/**", "dynamicLinks": true }
*/
	private void registerWithEmail() {
		if(!CommonOperationHelper.isValidEmail(binding
				.editUserName
				.getText()
				.toString())){

		}
		else{
			auth.createUserWithEmailAndPassword(
					binding.editUserName
							.getText()
							.toString(),
					binding
							.editPassword
							.getText()
							.toString()
			).addOnCompleteListener(task -> {
			FirebaseDatabase.getInstance()
					.getReference("Users")
					.child(auth.getCurrentUser().getUid())
					.setValue(new User(binding.editUserName
							.getText()
							.toString(),
							binding
									.editPassword
									.getText()
									.toString()))
					.addOnCompleteListener(task1 -> Toast.makeText(requireContext(), "User has been registered", Toast.LENGTH_SHORT).show());
			auth.getCurrentUser().sendEmailVerification();
		});}

	}

	private void registerWithPhoneNumber() {
		if(CommonOperationHelper.isValidPhoneNumber(binding
				.editUserName
				.getText()
				.toString(), binding
				.countryCodePicker
				.getSelectedCountryCode()).isValid())
		{
			sendVerificationCodeToUser();
		}
		else{
			//TODO Do some operation to show that user has entered an incorrect phone number
		}
	}

	private void sendVerificationCodeToUser() {
		FirebaseAuth auth = FirebaseAuth.getInstance();

		String phoneNumber = "+"+
				binding.countryCodePicker.getSelectedCountryCode()+binding.editUserName.getText().toString();
		PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(auth)
				.setPhoneNumber(phoneNumber)       // Phone number to verify
				.setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
				.setActivity(requireActivity())
				.setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
					@Override
					public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
						FirebaseDatabase.getInstance()
								.getReference("Users")
								.child(auth.getCurrentUser().getUid())
								.setValue(new User(binding.editUserName.getText().toString(), binding.editPassword.getText().toString()))
								.addOnCompleteListener(task1 -> Toast.makeText(requireContext(), "User has been registered", Toast.LENGTH_SHORT).show());
						Toast.makeText(requireActivity().getApplicationContext(), "Verification Completed", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
						//TODO Remove the below line or replace with a log (has been set for debugging)
						Toast.makeText(requireActivity().getApplicationContext(), e.getMessage()+"\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					}
				})
				.build());
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
		//TODO Delete comments for release version
		/*else if(password.length() < 8)
			binding.editPassword.setError("Must have at least 8 symbols!");
*/
		viewModel.addAccountInformations(userName,password);
	}

	private boolean isEmailValid(String email){
		return Patterns.EMAIL_ADDRESS.matcher(email).matches();

	}
}