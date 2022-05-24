package com.arif.cabex.MVVM;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.arif.cabex.event.MoveToOTPEvent;
import com.arif.cabex.helper.CommonOperationHelper;
import com.arif.cabex.model.User;
import com.google.firebase.FirebaseException;
import com.arif.cabex.network.MyFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

public class RegisterViewModel extends ViewModel {
    public RegisterRepository repo;
    FragmentActivity activity;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private boolean isEmailSelected=false,isVerified=false;
    String userName,password,countryCode;
    MyFirebase myFirebase = new MyFirebase();


    public void onVerification(String userName, String password, String countryCode, boolean isEmailSelected,FragmentActivity activity){
        this.userName=userName;
        this.password=password;
        this.countryCode=countryCode;
        this.isEmailSelected=isEmailSelected;
        this.activity=activity;
        if(isEmailSelected)
            registerWithEmail();
        else
            registerWithPhoneNumber();

    }


    private void registerWithEmail() {
        if(!CommonOperationHelper.isValidEmail(userName)){
            Toast.makeText(activity, "E-poçtunuz düz deyil, yoxlayıb yenidən cəhd edin!", Toast.LENGTH_SHORT).show();
        }
        else{
            myFirebase.registerWithEmail(userName,password,activity);
        }


    }

    private void registerWithPhoneNumber() {
        if(CommonOperationHelper.isValidPhoneNumber(userName, countryCode).isValid())
        {
            myFirebase.registerWithPhoneNumber(countryCode,activity,userName,password);
            EventBus.getDefault().postSticky(new MoveToOTPEvent());
        }
        else{
            Toast.makeText(activity, "Telefon nömrəniz düz deyil, yoxlayıb yenidən cəhd edin!", Toast.LENGTH_SHORT).show();
        }
    }






}
