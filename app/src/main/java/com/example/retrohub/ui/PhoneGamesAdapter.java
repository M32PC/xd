package com.example.retrohub.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrohub.R;
import com.example.retrohub.model.PhoneGameItem;

import java.util.List;

public class PhoneGamesAdapter extends RecyclerView.Adapter<PhoneGamesAdapter.PhoneGameVH> {

    public interface OnPhoneGameClickListener {
        void onClick(PhoneGameItem item);
    }

    private final List<PhoneGameItem> items;
    private final OnPhoneGameClickListener listener;

    public PhoneGamesAdapter(List<PhoneGameItem> items, OnPhoneGameClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhoneGameVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone_game, parent, false);
        return new PhoneGameVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneGameVH holder, int position) {
        PhoneGameItem item = items.get(position);
        holder.appName.setText(item.getAppName());
        holder.appIcon.setImageDrawable(item.getIcon());
        holder.itemView.setOnClickListener(v -> listener.onClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PhoneGameVH extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName;

        PhoneGameVH(@NonNull View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.appIcon);
            appName = itemView.findViewById(R.id.appName);
        }
    }
}
