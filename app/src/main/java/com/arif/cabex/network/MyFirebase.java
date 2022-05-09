package com.arif.cabex.network;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arif.cabex.event.CodeSentEvent;
import com.arif.cabex.event.EndRegistrationEvent;
import com.arif.cabex.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class MyFirebase {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public void registerWithPhoneNumber(String countryCode, Activity activity, String basePhoneNumber, String password){
        String phoneNumber = "+"+
                countryCode+basePhoneNumber;
        PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                        Log.d("MyTagHere", "onVerificationCompleted: Verification is Completed now!");
                        FirebaseDatabase.getInstance()
                                .getReference("Users")
                                .child(auth.getCurrentUser().getUid())
                                .setValue(new User(basePhoneNumber, password));
                    }

                    @Override
                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                        //TODO Remove the below line or replace with a log (has been set for debugging)
                        Toast.makeText(activity.getApplicationContext(), e.getMessage()+"\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Log.d("MyTagHere", "onCodeSent: "+s);
                        EventBus.getDefault().postSticky(new CodeSentEvent(s));
                        super.onCodeSent(s, forceResendingToken);

                    }
                })
                .build());
    }

    public void registerWithEmail(String userName, String password){
        auth.createUserWithEmailAndPassword(
                userName,
                password)
                .addOnCompleteListener(task -> {
                    FirebaseDatabase.getInstance()
                            .getReference("User")
                            .child(auth.getCurrentUser().getUid())
                            .setValue(new User(userName,
                                    password))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           //TODO confirm verification and close target vindow
                                                           Log.d("MyTagHere", "onComplete: Verification with email is confirmed!");
                                                       }
                                                   }
                            );
                    auth.getCurrentUser().sendEmailVerification();
                });
    }


    public void verifyWithCredential(PhoneAuthCredential credential){
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                EventBus.getDefault().postSticky(new EndRegistrationEvent());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MyTagHere", "Verification failed: "+e.getMessage());
            }
        });
    }
}
