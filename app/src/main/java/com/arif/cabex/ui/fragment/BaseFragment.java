package com.arif.cabex.ui.fragment;

import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

public class BaseFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
