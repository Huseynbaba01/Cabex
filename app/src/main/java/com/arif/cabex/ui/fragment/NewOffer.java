package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cabex.R;
import com.arif.cabex.database.NewOfferDatabase;
import com.arif.cabex.databinding.FragmentNewOfferBinding;
import com.arif.cabex.network.MyFirebase;

public class NewOffer extends Fragment {
    private FragmentNewOfferBinding binding;
    MyFirebase myFirebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewOfferBinding.inflate(inflater);
        myFirebase = new MyFirebase();

        setClickListeners();

        return binding.getRoot();
    }

    private void setClickListeners() {
        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTextBoxes()) {
                    myFirebase.addNewOffer(new NewOfferDatabase(binding.beginningPoint.getText().toString(), binding.lastPoint.getText().toString(), binding.leavingTime1.getText().toString(), binding.leavingTime2.toString(), binding.paymentOffer.getText().toString(), binding.yourLocation.getText().toString(), binding.additionalNotes.getText().toString(), false));
                    //TODO return to main page
                }
            }
        });
    }

    private boolean checkTextBoxes() {
        if(binding.beginningPoint.getText().toString().isEmpty()){
            binding.beginningPoint.requestFocus();
            return false;
        }
        if(binding.lastPoint.getText().toString().isEmpty()){
            binding.lastPoint.requestFocus();
            return false;
        }
        if(binding.leavingTime1.getText().toString().isEmpty()){
            binding.leavingTime1.requestFocus();
            return false;
        }
        if(binding.leavingTime2.getText().toString().isEmpty()){
            binding.leavingTime2.requestFocus();
            return false;
        }
        if(binding.paymentOffer.getText().toString().isEmpty()){
            binding.paymentOffer.requestFocus();
            return false;
        }
        if(binding.yourLocation.getText().toString().isEmpty()){
            binding.yourLocation.requestFocus();
            return false;
        }

        return true;

    }
}