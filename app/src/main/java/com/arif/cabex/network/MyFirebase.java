package com.arif.cabex.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arif.cabex.database.NewOfferDatabase;
import com.arif.cabex.event.ClearEditBoxesEvent;
import com.arif.cabex.event.EndRegistrationEvent;
import com.arif.cabex.event.MoveToOTPFromForgetPasswordEvent;
import com.arif.cabex.event.ResendPasswordWithEmailEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MyFirebase {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    FirebaseDatabase firebaseDatabase;
    private final String TAG = "MyTagHere";


    public void registerWithPhoneNumber(Activity activity, String phoneNumber){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        phoneNumber = "+"+phoneNumber;
        PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                        Log.d("MyTagHere", "onVerificationCompleted: Verification is Completed now!");
                    }

                    @Override
                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                        //TODO Remove the below line or replace with a log (has been set for debugging)
                        Toast.makeText(activity.getApplicationContext(), e.getMessage()+"\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Log.d(TAG, "onCodeSent: "+s);
                        myEdit.putString("verificationCode",s);
                        myEdit.apply();
                        super.onCodeSent(s, forceResendingToken);

                    }
                })
                .build());
    }

    public void registerWithEmail(String userName, String password,Context context){
        auth.createUserWithEmailAndPassword(
                userName,
                password)
                .addOnCompleteListener(task -> {

                    Toast.makeText(context, "E-poçtunuzu yoxlayın və gələn linkdən qeydiyyatı təsdiqləyin.", Toast.LENGTH_LONG).show();
                    if(task.isSuccessful()) {
                        EventBus.getDefault().postSticky(new ClearEditBoxesEvent());
                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                addEmailAndPasswordToGlobalDatabase(userName,password);
                            }
                        });

                    }
                });
    }


    public void verifyWithCredential(PhoneAuthCredential credential,String phoneNumber,String password){
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                addPhoneNumberAndPasswordToGlobalDatabase(phoneNumber,password);
                EventBus.getDefault().postSticky(new EndRegistrationEvent());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MyTagHere", "Verification failed: "+e.getMessage());
            }
        });
    }

    public void addPhoneNumberAndPasswordToGlobalDatabase(String phoneNumber,String password){
        HashMap<String,String> user = new HashMap<>();
        user.put("phoneNumber",phoneNumber);
        user.put("password",password);
        fireStore.collection("UsersWithPhoneNumber")
                .add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d(TAG, "onComplete: addingTo Email is completed!");
                    }
                });

    }

    public void addEmailAndPasswordToGlobalDatabase(String email,String password){
                HashMap<String,String> user = new HashMap<>();
                user.put("email",email);
                user.put("password",password);
                fireStore.collection("UsersWithEmail").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                });
    }


    public void sendPasswordResetEmail(String email, Context context){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("MyTagHere", "onComplete: Email Resend Confirmed");
                if(task.isSuccessful())
                    EventBus.getDefault().postSticky(new ResendPasswordWithEmailEvent());
                else
                    Toast.makeText(context, "E-poçtunuz düz deyil və ya qeydiyyatdan keçməyib, yoxlayıb yenidən cəhd edin!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MyTagHere", "onFailure: Your password resend did not succeeded: "+e.getMessage());
            }
        });
    }


    public void signInWithEmail(String email,String password){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //TODO Confirm the sign in and you will be able to move to another page
                        Log.d(TAG, "onComplete: you perfectly signed in!");
                    }
                });
    }


    public void signWithPhoneNumber(String phoneNumber,String password,Context mContext){
        fireStore.collection("UsersWithPhoneNumber").whereEqualTo("phoneNumber",phoneNumber).whereEqualTo("password",password).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(!task.getResult().isEmpty()){
                            //TODO sign in with phone number completed
                            Log.d(TAG, "onComplete: Sign in is completed!");
                        }else
                            Toast.makeText(mContext,"Your data is not exists!",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailureDuringSignINWithPhoneNumber: "+e.getMessage());
                    }
                });

    }


    public void addNewOffer(NewOfferDatabase newOfferDatabase){
        DatabaseReference reference = firebaseDatabase.getReference("PassengersOffer");
        reference.setValue(newOfferDatabase);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(NewOfferDatabase.class).getBeginningPoint();
                Log.d(TAG, "onDataChange: "+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void searchExistenceOfPhoneNumberFromFirebase(String phoneNumber, Context mContext){
        fireStore.collection("UsersWithPhoneNumber").whereEqualTo("phoneNumber",phoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(!task.getResult().isEmpty()){
                            registerWithPhoneNumber((Activity) mContext,phoneNumber);
                            EventBus.getDefault().postSticky(new MoveToOTPFromForgetPasswordEvent());
                        }else
                            Toast.makeText(mContext,"Telefon nömrəniz düzgün deyil!",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> Log.d(TAG, "onFailureDuringSignInWithPhoneNumber: "+e.getMessage()));
    }



    public void logOut(){
        auth.signOut();
    }
}
