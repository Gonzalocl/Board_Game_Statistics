package com.gonzalocl.boardgamestatistics.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzalocl.boardgamestatistics.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerList extends RecyclerView.Adapter<PlayerList.PlayerView> {

    private List<String> players;
    private PlayerList.OnClickListener onClickListener;

    static class PlayerView extends RecyclerView.ViewHolder {
        private TextView playerView;
        PlayerView(TextView view) {
            super(view);
            playerView = view;
        }
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    PlayerList(String[] players) {
        this.players = new ArrayList<>();
        Collections.addAll(this.players, players);
    }

    @NonNull
    @Override
    public PlayerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false);
        view.setTextColor(0xFFFFFFFF); // Very tricky
        return new PlayerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerView holder, final int position) {
        holder.playerView.setText(players.get(position));
        holder.playerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void deletePlayer(int i) {
        players.remove(i);
        notifyItemRemoved(i);
    }

    public void addPlayer(int i, String player) {
        players.add(i, player);
        notifyItemInserted(i);
    }

    public void clearPlayers() {
        int size = players.size();
        players.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
