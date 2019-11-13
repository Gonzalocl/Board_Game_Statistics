package com.gonzalocl.boardgamestatistics.app;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzalocl.boardgamestatistics.R;

public class PlayerList extends RecyclerView.Adapter<PlayerList.PlayerView> {

    private String[] players;

    public static class PlayerView extends RecyclerView.ViewHolder {
        public TextView playerName;
        public PlayerView(TextView view) {
            super(view);
            playerName = view;
        }
    }

    public PlayerList(String[] players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_view, parent, false);
        return new PlayerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerView holder, int position) {
        holder.playerName.setText(players[position]);
    }

    @Override
    public int getItemCount() {
        return players.length;
    }
}
