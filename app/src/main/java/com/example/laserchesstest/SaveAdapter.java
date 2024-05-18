package com.example.laserchesstest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.SaveViewHolder> {

    private final String[] savedGames;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SaveAdapter(String[] savedGames, OnItemClickListener listener) {
        this.savedGames = savedGames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_item, parent, false);
        return new SaveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveViewHolder holder, int position) {
        holder.saveTextView.setText(savedGames[position]);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return savedGames.length;
    }

    static class SaveViewHolder extends RecyclerView.ViewHolder {
        TextView saveTextView;

        SaveViewHolder(View itemView) {
            super(itemView);
            saveTextView = itemView.findViewById(R.id.saveTextView);
        }
    }
}
