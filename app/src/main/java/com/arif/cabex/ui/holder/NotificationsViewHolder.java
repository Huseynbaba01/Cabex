package com.arif.cabex.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.arif.cabex.databinding.ListitemNotificationsBinding;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsViewHolder extends RecyclerView.ViewHolder {
    private CardView card;
    private CircleImageView pp;
    private TextView info;
    private MaterialButton button;
    public NotificationsViewHolder(ListitemNotificationsBinding binding) {
        super(binding.getRoot());
        binding = ListitemNotificationsBinding.bind(itemView);
        card = binding.card;
        pp = binding.pp;
        info = binding.txtInfo;
        button = binding.btnAction;
    }

    public CardView getCard() {
        return card;
    }

    public void setCard(CardView card) {
        this.card = card;
    }

    public CircleImageView getPp() {
        return pp;
    }

    public void setPp(CircleImageView pp) {
        this.pp = pp;
    }

    public TextView getInfo() {
        return info;
    }

    public void setInfo(TextView info) {
        this.info = info;
    }

    public MaterialButton getButton() {
        return button;
    }

    public void setButton(MaterialButton button) {
        this.button = button;
    }
}
