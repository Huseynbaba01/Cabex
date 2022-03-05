package com.arif.cabex.MVVM;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import com.arif.cabex.databinding.FragmentRegisterBinding;
import com.arif.cabex.helper.CommonOperationHelper;
import com.arif.cabex.model.LoginData;
import com.arif.cabex.model.User;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;



public class RegisterViewModel extends ViewModel {
    public Repository repo;
    FragmentRegisterBinding binding;
    FragmentActivity activity;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private boolean isEmailSelected=false,isVerified=false;
    String userName,password,countryCode;


    public boolean onVerification(FragmentRegisterBinding binding,String userName, String password, String countryCode, boolean isEmailSelected, FragmentActivity activity){
        this.userName=userName;
        this.password=password;
        this.countryCode=countryCode;
        this.isEmailSelected=isEmailSelected;
        this.activity=activity;
        this.binding=binding;
        if(!isValidPassword(password)){
            return false;
        }
        if(!isEmailSelected)
            registerWithEmail();
        else
            registerWithPhoneNumber();

        return isVerified;
    }

    private boolean isValidPassword(String password){
        this.password = password;
        if(password.length()<6) {
            binding.editPassword.setError("Password must contain at least 6 characters");
            return false;
        }else if(!password.matches(".*[A-z].*")){
            binding.editPassword.setError("Password must contain letter");
            return false;
        }else if(!password.matches(".*[0-9].*")){
            binding.editPassword.setError("Password must contain number");
            return false;
        }else if(!password.matches(".*[!@#$&,./:;=?^_`|].*")){
            binding.editPassword.setError("Password must contain special character");
            return false;
        }else {
            return true;
        }
    }

    private void registerWithEmail() {
        if(!CommonOperationHelper.isValidEmail(userName)){
            //TODO
        }
        else{
            auth.createUserWithEmailAndPassword(
                    userName,
                    password)
                    .addOnCompleteListener(task -> {
                        FirebaseDatabase.getInstance()
                                .getReference("User")
                                .child(auth.getCurrentUser().getUid())
                                .setValue(new User(userName,
                                        password))
                                .addOnCompleteListener(task1 -> isVerified=true);
                        auth.getCurrentUser().sendEmailVerification();
                    });
        }


    }

    private void registerWithPhoneNumber() {

        if(CommonOperationHelper.isValidPhoneNumber(userName, countryCode).isValid())
        {
            sendVerificationCodeToUser();
        }
        else{
            //TODO Do some operation to show that user has entered an incorrect phone number
        }
    }

    private void sendVerificationCodeToUser() {
        String phoneNumber = "+"+
                countryCode+userName;
        PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                        FirebaseDatabase.getInstance()
                                .getReference("Users")
                                .child(auth.getCurrentUser().getUid())
                                .setValue(new User(userName, password))
                                .addOnCompleteListener(task1 -> isVerified=true);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                        //TODO Remove the below line or replace with a log (has been set for debugging)
                        isVerified=false;
                        Toast.makeText(activity.getApplicationContext(), e.getMessage()+"\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build());
    }


}
