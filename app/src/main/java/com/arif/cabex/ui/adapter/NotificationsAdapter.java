package com.arif.cabex.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arif.cabex.R;
import com.arif.cabex.databinding.ListitemNotificationsBinding;
import com.arif.cabex.helper.type.NotificationType;
import com.arif.cabex.model.NotificationItem;
import com.arif.cabex.ui.holder.NotificationsViewHolder;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsViewHolder> {
    private List<NotificationItem> items;
    private Context context;

    public NotificationsAdapter(Context context) {
        this.context = context;
    }

    public List<NotificationItem> getItems() {
        return items;
    }

    public void setItems(List<NotificationItem> items) {
        this.items = items;
    }

    public NotificationsAdapter(Context context, List<NotificationItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationsViewHolder(ListitemNotificationsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {
        NotificationItem item = items.get(position);

        if(!item.isRead()) holder.getCard().setCardBackgroundColor(context.getColor(R.color.secondary_main_color));
        else holder.getCard().setCardBackgroundColor(Color.WHITE);

        holder.getButton().setEnabled(!item.isRead());
        holder.getPp().setImageBitmap(item.getBitmap());
        switch (item.getType()){
             case NOTIFICATION_TYPE_CAR_LEFT:
                 holder.getInfo().setText(R.string.title_car_left);
                 holder.getButton().setText(R.string.title_follow_on_the_map);
                 break;
             case NOTIFICATION_TYPE_CAR_CLOSE:
                 holder.getInfo().setText(R.string.title_car_gets_closer);
                 holder.getButton().setText(R.string.title_follow_on_the_map);
                 break;
             case NOTIFICATION_TYPE_CAR_THERE:
                 holder.getInfo().setText(R.string.title_car_out_there);
                 holder.getButton().setText(R.string.title_follow_on_the_map);
                 break;
         }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
