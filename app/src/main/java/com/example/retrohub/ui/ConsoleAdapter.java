package com.example.retrohub.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrohub.R;
import com.example.retrohub.model.ConsoleItem;

import java.util.List;

public class ConsoleAdapter extends RecyclerView.Adapter<ConsoleAdapter.ConsoleVH> {

    public interface OnConsoleClickListener {
        void onConsoleClick(ConsoleItem item);
    }

    private final List<ConsoleItem> consoles;
    private final OnConsoleClickListener listener;
    private int focusedPosition = RecyclerView.NO_POSITION;

    public ConsoleAdapter(List<ConsoleItem> consoles, OnConsoleClickListener listener) {
        this.consoles = consoles;
        this.listener = listener;
    }

    public void setFocusedPosition(int position) {
        int old = focusedPosition;
        focusedPosition = position;
        if (old != RecyclerView.NO_POSITION) {
            notifyItemChanged(old);
        }
        if (focusedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(focusedPosition);
        }
    }

    @NonNull
    @Override
    public ConsoleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_console, parent, false);
        return new ConsoleVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsoleVH holder, int position) {
        ConsoleItem item = consoles.get(position);
        holder.name.setText(item.getDisplayName());
        holder.icon.setImageResource(item.getIconResId());
        holder.itemView.setOnClickListener(v -> listener.onConsoleClick(item));

        float scale = (position == focusedPosition) ? 1.08f : 0.95f;
        holder.itemView.animate().scaleX(scale).scaleY(scale).setDuration(180).start();
    }

    @Override
    public int getItemCount() {
        return consoles.size();
    }

    static class ConsoleVH extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;

        ConsoleVH(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.consoleIcon);
            name = itemView.findViewById(R.id.consoleName);
        }
    }
}
