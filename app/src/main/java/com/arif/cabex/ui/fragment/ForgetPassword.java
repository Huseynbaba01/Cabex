package com.arif.cabex.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.databinding.FragmentForgetPasswordBinding;
import com.arif.cabex.event.ResendPasswordWithEmailEvent;
import com.arif.cabex.helper.CommonOperationHelper;
import com.arif.cabex.network.MyFirebase;
import com.arif.cabex.ui.activity.MainPagesActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class ForgetPassword extends BaseFragment {
    FragmentForgetPasswordBinding binding;
    private boolean isEmailSection;
    private String TAG = "MyTagHere";
    private MyFirebase myFirebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetPasswordBinding.inflate(inflater);
        myFirebase = new MyFirebase();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        isEmailSection = sharedPreferences.getString("section","email").equals("email");


        addTexts();
        setClickListeners();

        return binding.getRoot();
    }

    private void addTexts() {
        if(isEmailSection){
            binding.textCenter.setText("Qeydiyyatdan keçdiyiniz e-poçt adresinizi qeyd edin, həmin adresə yeni şifrə göndəriləcək");
            binding.editCenter.setHint("E-poçt");
            binding.countryCodePicker.setVisibility(View.GONE);
            binding.editCenter.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        else{
            binding.textCenter.setText("Qeydiyyatdan keçdiyiniz telefon nömrənizi qeyd edin, həmin nömrənizə yeni şifrə göndəriləcək");
            binding.editCenter.setHint("Telefon nömrəsi");
            binding.editCenter.setInputType(InputType.TYPE_CLASS_NUMBER);
            binding.editCenter.setLetterSpacing(0.2F);

        }
    }

    private void setClickListeners() {
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });



        binding.send.setOnClickListener(this::releaseAccount);
    }

    private void releaseAccount(View view) {
        if(binding.editCenter.getText().toString().equals("")){
            if(isEmailSection)
            Toast.makeText(getActivity(), "E-poçt ünvanı boş ola bilməz, yazıb yenidən yoxlayın!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Telefon nömrəsi boş ola bilməz, yazıb yenidən yoxlayın!", Toast.LENGTH_SHORT).show();

            return;
        }

        sendPassword();
        }

    private void sendPassword() {
        if (isEmailSection && CommonOperationHelper.isValidEmail(binding.editCenter.getText().toString())) {
            //todo send new password to email
            myFirebase.sendPasswordResetEmail(binding.editCenter.getText().toString(), requireContext());
            Log.d(TAG, "releaseAccount: email is valid");
        } else if (CommonOperationHelper.isValidPhoneNumber(binding.editCenter.getText().toString(), binding.countryCodePicker.getSelectedCountryCode()).isValid()) {
            //TODO send new password to phone number

            Log.d(TAG, "releaseAccount: phone number is valid");
        } else {
            if (isEmailSection)
                Toast.makeText(getActivity(), "E-poçt ünvanı mövud deyil, yenidən yoxlayın!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Telefon nömrəsi düzgün deyil, yenidən yoxlayın!", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEmailPasswordResend(ResendPasswordWithEmailEvent resendPasswordWithEmailEvent){
        Intent intent =new Intent(requireContext(), MainPagesActivity.class);
        startActivity(intent);
    }
}