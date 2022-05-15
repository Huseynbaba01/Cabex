package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentNewOfferBinding;

public class NewOffer extends Fragment {
    private FragmentNewOfferBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewOfferBinding.inflate(inflater);

        return binding.getRoot();
    }
}