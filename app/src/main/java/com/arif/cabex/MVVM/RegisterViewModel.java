package com.arif.cabex.MVVM;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.arif.cabex.helper.CommonOperationHelper;
import com.arif.cabex.network.firebase.MyFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterViewModel extends ViewModel {
    public Repository repo;
    FragmentActivity activity;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private boolean isEmailSelected=false,isVerified=false;
    String userName,password,countryCode;
    MyFirebase myFirebase=new MyFirebase();
    Context mContext;


    public void onVerification(String userName, String password, String countryCode, boolean isEmailSelected, FragmentActivity activity, Context context){
        this.userName=userName;
        this.password=password;
        this.countryCode=countryCode;
        this.isEmailSelected=isEmailSelected;
        this.activity=activity;
        mContext=context;

        myFirebase.initializeApp(mContext);

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
            Log.d(TAG, "registerWithPhoneNumber: Your phone number is valid!");
            String phoneNumber="+"+ countryCode+userName;
            myFirebase.sendVerificationToPhoneNumber(phoneNumber,activity);
        }
    }


}
