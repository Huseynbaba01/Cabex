package com.arif.cabex.ui.fragment;

import static com.arif.cabex.R.drawable.ic_baseline_phone_android_24;
import static com.arif.cabex.R.drawable.ic_email;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.MVVM.RegisterViewModel;
import com.arif.cabex.databinding.FragmentRegisterBinding;
import com.arif.cabex.event.ClearEditBoxesEvent;
import com.arif.cabex.event.MoveToOTPEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RegisterFragment extends BaseFragment {
	private String TAG  = "MyTagHere";

	private FragmentRegisterBinding binding;
	private RegisterViewModel viewModel=new RegisterViewModel();
	private boolean isEmailSelected;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor myEdit;

	@Override
	public void onStart() {
		super.onStart();
//		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//		if(currentUser != null){
//			Intent intent = new Intent(requireContext(), MainPagesActivity.class);
//			startActivity(intent);
//		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentRegisterBinding.inflate(inflater, container, false);

		sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
		myEdit = sharedPreferences.edit();
		isEmailSelected = sharedPreferences.getString("section","email").equals("email");

		Log.d(TAG, "onCreateView: "+isEmailSelected);

		startUI();
		setListeners();

		return binding.getRoot();
	}

	private void startUI(){
		Log.d(TAG, "startUI: "+sharedPreferences.getString("section",""));
		if(sharedPreferences.getString("section","").equals("phoneNumber")) {
			binding.mainIcon.setBackgroundDrawable(getContext().getResources().getDrawable(ic_baseline_phone_android_24));
			binding.countryCodePicker.setVisibility(View.VISIBLE);
			binding.userName.setInputType(InputType.TYPE_CLASS_NUMBER);
			binding.userName.setHint("Telefon nömrəsi");
			binding.cardPhoneNumber.setVisibility(View.VISIBLE);
			binding.cardEmail.setVisibility(View.GONE);
			binding.countryCodePicker.setVisibility(View.VISIBLE);
			return;
		}

		isEmailSelected=true;
		binding.mainIcon.setBackgroundDrawable(getContext().getResources().getDrawable(ic_email));
		binding.userName.setInputType(InputType.TYPE_CLASS_TEXT);
		binding.countryCodePicker.setVisibility(View.GONE);
		binding.userName.setHint("E-poçt");
		binding.cardEmail.setVisibility(View.VISIBLE);
		binding.cardPhoneNumber.setVisibility(View.GONE);
		binding.countryCodePicker.setVisibility(View.GONE);
		myEdit.putString("section","email");
		myEdit.apply();

	}

	private void setListeners(){
		binding.sectionPhone.setOnClickListener(this::onSectionPhoneClicked);
		binding.sectionEmail.setOnClickListener(this::onSectionEmailClicked);
		binding.signUp.setOnClickListener(this::onRegister);
		binding.loginLink.setOnClickListener(this::moveToLogin);

		binding.editPassword.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				binding.passwordLayout.setPasswordVisibilityToggleEnabled(true);
			}
		});
	}

	private void moveToLogin(View view) {
		NavHostFragment.findNavController(this).navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment());
	}


	private void onSectionEmailClicked(View view) {
		if(!isEmailSelected){
			isEmailSelected=true;
			binding.mainIcon.setBackgroundDrawable(getContext().getResources().getDrawable(ic_email));
			binding.userName.setInputType(InputType.TYPE_CLASS_TEXT);

			binding.countryCodePicker.setVisibility(View.GONE);

			binding.userName.setHint("E-poçt");

			binding.cardEmail.setVisibility(View.VISIBLE);
			binding.cardPhoneNumber.setVisibility(View.GONE);
			binding.countryCodePicker.setVisibility(View.GONE);
			myEdit.putString("section","email");
			myEdit.apply();
		}
	}

	private void onSectionPhoneClicked(View view) {
		if(isEmailSelected){
			isEmailSelected=false;
			binding.mainIcon.setBackgroundDrawable(getContext().getResources().getDrawable(ic_baseline_phone_android_24));

			binding.countryCodePicker.setVisibility(View.VISIBLE);
			binding.userName.setInputType(InputType.TYPE_CLASS_NUMBER);


			binding.userName.setHint("Telefon nömrəsi");
			binding.cardPhoneNumber.setVisibility(View.VISIBLE);
			binding.cardEmail.setVisibility(View.GONE);
			binding.countryCodePicker.setVisibility(View.VISIBLE);
			myEdit.putString("section","phoneNumber");
			myEdit.apply();
		}
	}

	private void onRegister(View view) {
		String userName=binding.userName.getText().toString();
		String password=binding.editPassword.getText().toString();
		if(userName.isEmpty()){
			if(isEmailSelected)
			Toast.makeText(getActivity(), "E-poçt boş ola bilməz!", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getActivity(), "Telefon nömrəsi boş ola bilməz!", Toast.LENGTH_SHORT).show();
			return;
		}
		if(password.isEmpty()){
			binding.passwordLayout.setPasswordVisibilityToggleEnabled(false);
			binding.editPassword.setError("Şifrə boş ola bilməz!");

		}
		viewModel.onVerification(userName
				,password
				,binding.countryCodePicker.getSelectedCountryCode()
				,isEmailSelected
				,requireActivity());

	}


	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMoveToOTPEvent(MoveToOTPEvent moveToOTPEvent){
		myEdit.putString("phoneNumber",binding.countryCodePicker.getSelectedCountryCode()+binding.editPassword.getText().toString());
		myEdit.putString("password",binding.editPassword.getText().toString());
		myEdit.putBoolean("fromRegister",true);
		myEdit.apply();
		NavHostFragment.findNavController(this).navigate(RegisterFragmentDirections.actionRegisterFragmentToOTPFragment());
	}

	@Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
	public void onClearEditBoxesEvent(ClearEditBoxesEvent clearEditBoxesEvent){
		binding.userName.setText("");
		binding.editPassword.setText("");
	}

}