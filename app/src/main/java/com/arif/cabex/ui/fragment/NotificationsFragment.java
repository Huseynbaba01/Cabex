package com.arif.cabex.ui.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arif.cabex.R;
import com.arif.cabex.databinding.FragmentNotificationsBinding;
import com.arif.cabex.event.ChangeNavbarVisibilityEvent;
import com.arif.cabex.helper.type.NotificationType;
import com.arif.cabex.model.NotificationItem;
import com.arif.cabex.ui.adapter.NotificationsAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends BaseFragment {
	private FragmentNotificationsBinding binding;
	private List<NotificationItem> notificationItems;
	private NotificationsAdapter adapter;


	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		EventBus.getDefault().post(new ChangeNavbarVisibilityEvent(true));

		binding = FragmentNotificationsBinding.inflate(inflater, container, false);
		notificationItems = new ArrayList<>();
		notificationItems.add(new NotificationItem(false,
				BitmapFactory.decodeResource(getContext().getResources(),R.drawable.mock_profile), NotificationType.NOTIFICATION_TYPE_CAR_CLOSE));
		notificationItems.add(new NotificationItem(true,
				BitmapFactory.decodeResource(getContext().getResources(),R.drawable.mock_profile), NotificationType.NOTIFICATION_TYPE_CAR_THERE));
		notificationItems.add(new NotificationItem(true,
				BitmapFactory.decodeResource(getContext().getResources(),R.drawable.mock_profile), NotificationType.NOTIFICATION_TYPE_CAR_LEFT));
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		adapter = new NotificationsAdapter(getContext());
		binding.notificationItems.setAdapter(adapter);
		binding.notificationItems.setLayoutManager(new LinearLayoutManager(getContext()));
		adapter.setItems(notificationItems);
		binding.btnRead.setOnClickListener(view1 -> {
		});

	}
}