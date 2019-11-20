package com.gonzalocl.boardgamestatistics.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.app.UiEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuggestionsList extends RecyclerView.Adapter<SuggestionsList.ItemView> {

    private List<String> items;
    private View.OnClickListener onClickListener;

    static class ItemView extends RecyclerView.ViewHolder {
        private TextView itemView;
        ItemView(TextView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public SuggestionsList(String[] items) {
        this.items = new ArrayList<String>();
        Collections.addAll(this.items, items);
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView itemView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false);
        return new ItemView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, int position) {
        holder.itemView.setText(items.get(position));
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
