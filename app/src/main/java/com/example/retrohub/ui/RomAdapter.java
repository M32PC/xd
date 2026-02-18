package com.example.retrohub.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrohub.R;
import com.example.retrohub.model.RomEntry;

import java.util.ArrayList;
import java.util.List;

public class RomAdapter extends RecyclerView.Adapter<RomAdapter.RomVH> {

    public interface OnRomClickListener {
        void onRomClick(RomEntry rom);
    }

    private final List<RomEntry> data = new ArrayList<>();
    private final OnRomClickListener clickListener;

    public RomAdapter(OnRomClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submit(List<RomEntry> roms) {
        data.clear();
        data.addAll(roms);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RomVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rom, parent, false);
        return new RomVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RomVH holder, int position) {
        RomEntry entry = data.get(position);
        holder.name.setText(entry.getName());
        holder.itemView.setOnClickListener(v -> clickListener.onRomClick(entry));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class RomVH extends RecyclerView.ViewHolder {
        TextView name;

        RomVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.romName);
        }
    }
}
