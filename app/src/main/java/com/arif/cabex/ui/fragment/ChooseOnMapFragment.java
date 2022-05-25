package com.arif.cabex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cabex.R;
import com.arif.cabex.event.ChangeNavbarVisibilityEvent;
import com.arif.cabex.ui.activity.MainPagesActivity;

import org.greenrobot.eventbus.EventBus;

public class ChooseOnMapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().post(new ChangeNavbarVisibilityEvent(false));
        return inflater.inflate(R.layout.fragment_choose_on_map, container, false);
    }
}