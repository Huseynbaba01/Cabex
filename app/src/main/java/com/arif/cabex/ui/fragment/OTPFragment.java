package com.arif.cabex.ui.fragment;

import static android.os.Build.VERSION_CODES.R;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.databinding.FragmentOTPBinding;
import com.arif.cabex.event.OTPSentEvent;
import com.arif.cabex.helper.GenericTextWatcher;
import com.arif.cabex.network.firebase.MyFirebase;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.material.textfield.TextInputEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class OTPFragment extends BaseFragment implements View.OnKeyListener{
	private FragmentOTPBinding binding;
	MyFirebase myFirebase=new MyFirebase();
	private String realOTPCode = "";
	String verificationCode;
	MyFirebase firebase=new MyFirebase();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentOTPBinding.inflate(inflater, container, false);
		binding.btnConfirmation.setOnClickListener(this::onClickConfirmationButton);

		addTextChangedListeners();

		return binding.getRoot();
	}


	private void addTextChangedListeners(){
		binding.firstOtp.addTextChangedListener(new GenericTextWatcher(binding.firstOtp, binding.secondOtp));
		binding.secondOtp.addTextChangedListener(new GenericTextWatcher(binding.secondOtp, binding.thirdOtp));
		binding.thirdOtp.addTextChangedListener(new GenericTextWatcher(binding.thirdOtp, binding.fourthOtp));
		binding.fourthOtp.addTextChangedListener(new GenericTextWatcher(binding.fourthOtp, binding.fifthOtp));
		binding.fifthOtp.addTextChangedListener(new GenericTextWatcher(binding.fifthOtp, binding.sixthOtp));
		binding.sixthOtp.addTextChangedListener(new GenericTextWatcher(binding.sixthOtp, binding.sixthOtp));
		setOnDeleteProcess();

	}

	@SuppressLint("NewApi")
	private void setOnDeleteProcess() {
		binding.secondOtp.addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this);
		binding.thirdOtp.addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this);
		binding.fourthOtp.addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this);
		binding.fifthOtp.addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this);
		binding.sixthOtp.addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this);
	}



	private void onClickConfirmationButton(View view) {
		String enteredValue=getEnteredValue();
		if(enteredValue==verificationCode){
			firebase.registerUserToTheServer();
		}
	}

	private String getEnteredValue() {
		return new StringBuilder()
				.append(binding.firstOtp.getText())
				.append(binding.secondOtp.getText())
				.append(binding.thirdOtp.getText())
				.append(binding.fourthOtp.getText())
				.append(binding.fifthOtp.getText())
				.append(binding.sixthOtp.getText())
				.toString();
	}


	@Override
	public boolean onKey(View view, int i, KeyEvent keyEvent) {
		if(i==KeyEvent.KEYCODE_DEL && ((TextInputEditText) view).getText().toString().equals("")){
			if(binding.secondOtp.equals(view))
				binding.firstOtp.requestFocus();
			else if(binding.thirdOtp.equals(view))
				binding.secondOtp.requestFocus();
			else if(binding.fourthOtp.equals(view))
				binding.thirdOtp.requestFocus();
			else if(binding.fifthOtp.equals(view))
				binding.fourthOtp.requestFocus();
			else if(binding.sixthOtp.equals(view))
				binding.fifthOtp.requestFocus();
		}
		
		return true;
	}

	@Subscribe(sticky = true,threadMode = ThreadMode.ASYNC)
	public void getVerificationCode(OTPSentEvent otpSentEvent){
		verificationCode=otpSentEvent.getOtp();
		Toast.makeText(requireActivity(), verificationCode, Toast.LENGTH_SHORT).show();
	}
}