package com.arif.cabex.network.firebase;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arif.cabex.event.OTPSentEvent;
import com.arif.cabex.event.OTPVerifiedEvent;
import com.arif.cabex.event.getMainUserText;
import com.arif.cabex.event.getPassword;
import com.arif.cabex.model.RegisterData;
import com.arif.cabex.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

public class MyFirebase {
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

    String userName,password,verificationCode;


    public void initializeApp(Context context){
        FirebaseApp.initializeApp(context);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());
    }


    public void sendVerificationToEmail(String emailAddress, String password){
        auth.createUserWithEmailAndPassword(
                emailAddress,
                password)
                .addOnCompleteListener(task -> {
                    FirebaseDatabase.getInstance()
                            .getReference("User")
                            .child(auth.getCurrentUser().getUid())
                            .setValue(new User(emailAddress,
                                    password))
                            .addOnCompleteListener(task1 -> {
                                //TODO write some code when email and password are created in the server
                            });
                    auth.getCurrentUser().sendEmailVerification();
                });
        user.sendEmailVerification(ActionCodeSettings.newBuilder().build());
    }


    public void sendVerificationToPhoneNumber(String number, Activity activity){
        auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        Log.d(TAG, "sendVerificationToPhoneNumber: Phone number sent to the phone");
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder()
                .setPhoneNumber(number)
                .setActivity(activity)
                .setTimeout(30L, TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Log.d(TAG, "onVerificationCompleted: "+ phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.d(TAG, "onVerificationFailed: "+e);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Log.d(TAG, "onCodeSent: "+s);

                        //Event section
                        EventBus.getDefault().postSticky(new OTPVerifiedEvent());
                        EventBus.getDefault().postSticky(new OTPSentEvent(s));
                        verificationCode=s;
                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.ASYNC)
    public void getUser(getMainUserText userText){
        userName=userText.getMainText();
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.ASYNC)
    public void getUser(getPassword password1){
        password=password1.getPassword();
    }



    public void registerUserToTheServer(){
        FirebaseDatabase.getInstance().getReference("Users")
                .setValue(new RegisterData(userName,password))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: registration completed!");
                    }
                });
    }
}
