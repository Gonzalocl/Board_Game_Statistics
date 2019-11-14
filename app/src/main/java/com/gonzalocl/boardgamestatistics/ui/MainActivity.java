package com.gonzalocl.boardgamestatistics.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.app.PlayerList;
import com.gonzalocl.boardgamestatistics.app.UiEvents;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final UiEvents uiEvents = UiEvents.getUiEvents();

        Button buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiEvents.deletePlayer(0);
            }
        });


        Button addPlayer = findViewById(R.id.add_player);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiEvents.addPlayer(0);
            }
        });

        Button clearPlayerList = findViewById(R.id.clear_player_list);
        clearPlayerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiEvents.clearPlayerList();
            }
        });

        RecyclerView playerList = findViewById(R.id.player_list);
        playerList.setHasFixedSize(true);

        RecyclerView.LayoutManager playerListManager = new LinearLayoutManager(this);
        playerList.setLayoutManager(playerListManager);

        String[] ps = {"uno", "dos", "tres", "four", "five", "six", "seven", "ocho", "nueve", "diez"};
        RecyclerView.Adapter players = new PlayerList(ps);
        playerList.setAdapter(players);

    }
}
