package com.gonzalocl.boardgamestatistics.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.gonzalocl.boardgamestatistics.R;
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                players.deletePlayer(viewHolder.getAdapterPosition());
            }

        });

        itemTouchHelper.attachToRecyclerView(playerList);

        final UiEvents uiEvents = UiEvents.getUiEvents();

        FloatingActionButton buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiEvents.start();
            }
        });


        FloatingActionButton addPlayer = findViewById(R.id.add_player);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiEvents.addPlayer(0);
                players.addPlayer(0, String.format("%d", c));
                c++;
                SelectionActivity.start(MainActivity.this);
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
