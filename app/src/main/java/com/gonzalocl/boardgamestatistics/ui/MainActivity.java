package com.gonzalocl.boardgamestatistics.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.app.PlayerList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView playerList = findViewById(R.id.player_list);
        playerList.setHasFixedSize(true);

        RecyclerView.LayoutManager playerListManager = new LinearLayoutManager(this);
        playerList.setLayoutManager(playerListManager);

        String[] ps = {"uno", "dos", "tres", "four", "five", "six", "seven", "ocho", "nueve", "diez"};
        RecyclerView.Adapter players = new PlayerList(ps);
        playerList.setAdapter(players);

    }
}
