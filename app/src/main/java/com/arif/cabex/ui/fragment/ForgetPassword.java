package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentForgetPasswordBinding;
import com.arif.cabex.helper.CommonOperationHelper;

import java.util.Objects;

public class ForgetPassword extends Fragment {
    FragmentForgetPasswordBinding binding;
    private boolean isEmailSection;
    private String TAG = "MyTagHere";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetPasswordBinding.inflate(inflater);
        isEmailSection = ForgetPasswordArgs.fromBundle(getArguments()).getIsEmailSelected();

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
                Objects.requireNonNull(getActivity()).onBackPressed();
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
        if(isEmailSection&&CommonOperationHelper.isValidEmail(binding.editCenter.getText().toString())){
            //todo send new password to email
            Log.d(TAG, "releaseAccount: email is valid");
        }else if(CommonOperationHelper.isValidPhoneNumber(binding.editCenter.getText().toString(),binding.countryCodePicker.getSelectedCountryCode()).isValid()){
            //TODO send new password to phone number
            Log.d(TAG, "releaseAccount: phone number is valid");
        }else{
            if(isEmailSection)
                Toast.makeText(getActivity(), "E-poçt ünvanı mövud deyil, yenidən yoxlayın!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Telefon nömrəsi düzgün deyil, yenidən yoxlayın!", Toast.LENGTH_SHORT).show();
    }
}
}