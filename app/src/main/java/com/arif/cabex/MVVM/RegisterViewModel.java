package com.arif.cabex.MVVM;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.arif.cabex.helper.CommonOperationHelper;
import com.arif.cabex.network.firebase.MyFirebase;

public class RegisterViewModel extends ViewModel {
    private final String TAG = "RegisterViewModel";
    Activity activity;
    String userName,password,countryCode;
    MyFirebase myFirebase=new MyFirebase();


    public void onVerification(String userName, String password, String countryCode, boolean isEmailSelected, FragmentActivity activity, Context context){
        this.userName=userName;
        this.password=password;
        this.countryCode=countryCode;
        this.activity=activity;
        myFirebase.initializeApp(context);
        if(isEmailSelected)
            registerWithEmail();
        else
            registerWithPhoneNumber();
    }


    private void registerWithEmail() {
        if(!CommonOperationHelper.isValidEmail(userName)){
            Toast.makeText(activity, "Your email address is not valid!", Toast.LENGTH_SHORT).show();
        }
        else{
            myFirebase.sendVerificationToEmail(userName,password);
        }


    }

    private void registerWithPhoneNumber() {
        if(!CommonOperationHelper.isValidPhoneNumber(userName, countryCode).isValid())
        {
            Log.d(TAG, "registerWithPhoneNumber: Your phone number is not valid");
            Toast.makeText(activity, "Your phone number is not valid!", Toast.LENGTH_SHORT).show();
        }
        else{
            String phoneNumber="+"+ countryCode+userName;
            Log.d(TAG, "registerWithPhoneNumber: Your phone number is valid!"+phoneNumber);
            myFirebase.sendVerificationToPhoneNumber(phoneNumber, activity);
        }
    }


}
