package com.arif.cabex.network;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arif.cabex.database.NewOfferDatabase;
import com.arif.cabex.event.ClearEditBoxesEvent;
import com.arif.cabex.event.CodeSentEvent;
import com.arif.cabex.event.EndRegistrationEvent;
import com.arif.cabex.event.ResendPasswordWithEmailEvent;
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
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private String TAG = "MyTagHere";

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
                        Log.d(TAG, "onCodeSent: "+s);
                        EventBus.getDefault().postSticky(new CodeSentEvent(s));
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
                    EventBus.getDefault().postSticky(new ClearEditBoxesEvent());

                    if(task.isSuccessful())
                    FirebaseDatabase.getInstance()
                            .getReference("User")
                            .child(auth.getCurrentUser().getUid())
                            .setValue(new User(userName,
                                    password))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {

                                                       }
                                                   }

                            ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: "+e.getMessage());
                        }
                    });
                    auth.getCurrentUser().sendEmailVerification();
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


    public void signWithPhoneNumber(String phoneNumber,String password){
        fireStore.collection("UsersWithPhoneNumber").whereEqualTo("phoneNumber",phoneNumber).whereEqualTo("password",password).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult().isEmpty()){
                            //TODO sign in with phone number completed
                        }
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
        reference.setValue("Hello world");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d(TAG, "onDataChange: "+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Instance(){
        //TODO search quality of these codes,then return to addNewOffer method
        DatabaseReference reference = firebaseDatabase.getReference("PassengersOffer");
        reference.setValue("Hello world");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d(TAG, "onDataChange: "+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
