package com.arif.cabex.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.fragment.NavHostFragment;

import com.arif.cabex.databinding.FragmentOTPBinding;
import com.arif.cabex.event.EndRegistrationEvent;
import com.arif.cabex.helper.GenericTextWatcher;
import com.arif.cabex.network.MyFirebase;
import com.arif.cabex.ui.activity.MainPagesActivity;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class OTPFragment extends BaseFragment {
	private FragmentOTPBinding binding;

	private String verificationCode,phoneNumber,password,countryCode;
	MyFirebase myFirebase = new MyFirebase();
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor myEdit;
	private String TAG = "MyTagHere";
	private Boolean fromRegister;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentOTPBinding.inflate(inflater, container, false);
		sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
		phoneNumber = sharedPreferences.getString("phoneNumber","__");
		fromRegister = sharedPreferences.getBoolean("fromRegister",false);
		verificationCode = sharedPreferences.getString("verificationCode","00000");

		binding.confirmationHintNumber.setText("Kod +"+phoneNumber+"\n nömrəsinə göndərildi");
		addTextChangedListeners();
		setOnKeyListeners();
		setClickListeners();


		return binding.getRoot();
	}

	private void setClickListeners() {
		binding.btnConfirmation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String myVerificationCode = getVerificationCode();
				if(myVerificationCode.length()!=6){
					resetBoxes();
					Toast.makeText(getActivity(), "Bütün xanaları doldurun!", Toast.LENGTH_SHORT).show();
				}
				else{
					PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,myVerificationCode);
					myFirebase.verifyWithCredential(credential,countryCode+phoneNumber,password);
				}
			}
		});

		binding.resend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				myFirebase.registerWithPhoneNumber(getActivity(),phoneNumber);
				resetBoxes();
			}
		});
	}

	private void resetBoxes() {
		binding.firstOtp.setText("");
		binding.secondOtp.setText("");
		binding.thirdOtp.setText("");
		binding.fourthOtp.setText("");
		binding.fifthOtp.setText("");
		binding.sixthOtp.setText("");
		binding.firstOtp.requestFocus();
	}

	private String getVerificationCode() {
		StringBuilder sb = new StringBuilder();
		sb.append(binding.firstOtp.getText());
		sb.append(binding.secondOtp.getText());
		sb.append(binding.thirdOtp.getText());
		sb.append(binding.fourthOtp.getText());
		sb.append(binding.fifthOtp.getText());
		sb.append(binding.sixthOtp.getText());
		return sb.toString();
	}

	private void setOnKeyListeners() {
		binding.secondOtp.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent) {
				if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && binding.secondOtp.length() == 0){
					binding.firstOtp.requestFocus();
					binding.firstOtp.setSelection(binding.firstOtp.length());
				}
				return false;
			}
		});
		binding.thirdOtp.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent) {
				if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && binding.thirdOtp.length() == 0){
					binding.secondOtp.requestFocus();
					binding.secondOtp.setSelection(binding.secondOtp.length());
				}
				return false;
			}
		});
		binding.fourthOtp.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent) {
				if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && binding.fourthOtp.length() == 0){
					binding.thirdOtp.requestFocus();
					binding.thirdOtp.setSelection(binding.thirdOtp.length());
				}
				return false;
			}
		});
		binding.fifthOtp.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent) {
				if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && binding.fifthOtp.length() == 0){
					binding.fourthOtp.requestFocus();
					binding.fourthOtp.setSelection(binding.fourthOtp.length());
				}
				return false;
			}
		});
		binding.sixthOtp.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent) {
				if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && binding.sixthOtp.length() == 0){
					binding.fifthOtp.requestFocus();
					binding.fifthOtp.setSelection(binding.fifthOtp.length());
				}
				return false;
			}
		});
	}

	private void addTextChangedListeners(){
		binding.firstOtp.addTextChangedListener(new GenericTextWatcher(binding.firstOtp, binding.firstOtp, binding.secondOtp));
		binding.secondOtp.addTextChangedListener(new GenericTextWatcher(binding.firstOtp, binding.secondOtp, binding.thirdOtp));
		binding.thirdOtp.addTextChangedListener(new GenericTextWatcher(binding.secondOtp, binding.thirdOtp, binding.fourthOtp));
		binding.fourthOtp.addTextChangedListener(new GenericTextWatcher(binding.thirdOtp, binding.fourthOtp, binding.fifthOtp));
		binding.fifthOtp.addTextChangedListener(new GenericTextWatcher(binding.fourthOtp, binding.fifthOtp, binding.sixthOtp));
		binding.sixthOtp.addTextChangedListener(new GenericTextWatcher(binding.fifthOtp, binding.sixthOtp, binding.sixthOtp));
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onFinishRegistrationEvent(EndRegistrationEvent endRegistrationEvent){
		Log.d(TAG, "onFinishRegistrationEvent: Verification is completed (event)");
		if(fromRegister){
			Log.d(TAG, "onFinishRegistrationEvent: "+fromRegister);
			Intent intent = new Intent(getContext(), MainPagesActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}else{
			NavHostFragment.findNavController(this).navigate(OTPFragmentDirections.actionOTPFragmentToResetPasswordFragment());
		}

	}


}