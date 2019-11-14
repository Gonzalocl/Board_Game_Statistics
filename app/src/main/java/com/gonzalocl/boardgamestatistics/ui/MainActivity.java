package com.gonzalocl.boardgamestatistics.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.app.PlayerList;
import com.gonzalocl.boardgamestatistics.app.UiEvents;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    int c = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView playerList = findViewById(R.id.player_list);
        playerList.setHasFixedSize(true);

        RecyclerView.LayoutManager playerListManager = new LinearLayoutManager(this);
        playerList.setLayoutManager(playerListManager);

        String[] ps = {"uno", "dos", "tres", "four", "five", "six", "seven", "ocho", "nueve", "diez"};
        final PlayerList players = new PlayerList(ps);
        playerList.setAdapter(players);

        final UiEvents uiEvents = UiEvents.getUiEvents();

        FloatingActionButton buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiEvents.start();
                players.deletePlayer(0);
            }
        });


        FloatingActionButton addPlayer = findViewById(R.id.add_player);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiEvents.addPlayer(0);
                players.addPlayer(0, String.format("%d", c));
                c++;
            }
        });

        FloatingActionButton clearPlayerList = findViewById(R.id.clear_player_list);
        clearPlayerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiEvents.clearPlayerList();
                players.clearPlayers();
            }
        });

    }
}
