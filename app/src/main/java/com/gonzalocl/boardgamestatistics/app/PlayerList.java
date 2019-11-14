package com.gonzalocl.boardgamestatistics.app;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzalocl.boardgamestatistics.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerList extends RecyclerView.Adapter<PlayerList.PlayerView> {

    private List<String> players;

    public static class PlayerView extends RecyclerView.ViewHolder {
        public TextView playerName;
        public PlayerView(TextView view) {
            super(view);
            playerName = view;
        }
    }

    public PlayerList(String[] players) {
        this.players = new ArrayList<String>();
        Collections.addAll(this.players, players);
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
        holder.playerName.setText(players.get(position));
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

}
