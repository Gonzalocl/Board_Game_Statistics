package com.gonzalocl.boardgamestatistics.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.app.StatusService;
import com.gonzalocl.boardgamestatistics.app.BoardGameStatistics;

public class MainActivity extends AppCompatActivity {

    static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = prefs.getBoolean(SettingsActivity.preferences_theme_key, false);
        if(darkTheme) setTheme(R.style.DarkTheme);
        else setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_main);

        // TODO: idk
        Toolbar myToolbar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            myToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
        }

        final BoardGameStatistics boardGameStatistics = BoardGameStatistics.getBoardGameStatistics();

        final RecyclerView playerList = findViewById(R.id.player_list);
        playerList.setHasFixedSize(true);

        RecyclerView.LayoutManager playerListManager = new LinearLayoutManager(this);
        playerList.setLayoutManager(playerListManager);

        final PlayerList players = new PlayerList(boardGameStatistics.getCurrentPlayers());
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
                int position = viewHolder.getAdapterPosition();
                players.deletePlayer(position);
                boardGameStatistics.deletePlayer(position);
            }

        });

        itemTouchHelper.attachToRecyclerView(playerList);


        ImageButton buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardGameStatistics.start();
                Intent statusService = new Intent(MainActivity.this, StatusService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(statusService);
                } else {
                    // TODO test this
                    startService(statusService);
                }
            }
        });


        ImageButton addPlayer = findViewById(R.id.add_player);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectionActivity.start(MainActivity.this);
//                PlayerSelectionActivity.start(MainActivity.this);
            }
        });

        ImageButton clearPlayerList = findViewById(R.id.clear_player_list);
        clearPlayerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardGameStatistics.clearPlayerList();
                players.clearPlayers();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SettingsActivity.reloadTheme) {
            SettingsActivity.reloadTheme = false;
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = prefs.getBoolean(SettingsActivity.preferences_theme_key, false);
        // Inflate the menu; this adds items to the action bar if it is present.
        if(darkTheme) getMenuInflater().inflate(R.menu.menu_dark, menu);
        else getMenuInflater().inflate(R.menu.menu_light, menu);

        return true;
    }

    // App bar buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                SettingsActivity.start(MainActivity.this);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
