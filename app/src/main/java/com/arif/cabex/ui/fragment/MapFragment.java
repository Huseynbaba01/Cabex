package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cabex.R;

public class MapFragment extends Fragment {
	private final String TAG = "MapFragment";
	int a = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView: " + ++a);
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_map, container, false);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause: " + ++a);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: " + ++a);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy: "+ ++a);
	}
}