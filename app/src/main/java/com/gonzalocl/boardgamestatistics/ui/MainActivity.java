package com.gonzalocl.boardgamestatistics.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
        final PlayerList playerList = new PlayerList(boardGameStatistics.getCurrentPlayers());
        playerList.setOnClickListener(new PlayerList.OnClickListener() {
            @Override
            public void onClick(int position) {
                // TODO select team
            }
        });

        RecyclerView playerListView = findViewById(R.id.player_list);
        playerListView.setHasFixedSize(true);
        playerListView.setLayoutManager(new LinearLayoutManager(this));
        playerListView.setAdapter(playerList);

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
                playerList.deletePlayer(position);
                boardGameStatistics.deletePlayer(position);
            }

        });
        itemTouchHelper.attachToRecyclerView(playerListView);

        final ImageButton buttonStart = findViewById(R.id.button_start);
        final ImageButton buttonDiscard = findViewById(R.id.button_discard);

        DialogInterface.OnClickListener discardConfirmationListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    boardGameStatistics.discard();
                    buttonStart.setImageResource(R.drawable.play);
                    buttonDiscard.setVisibility(View.GONE);
                    StatusService.confirmResults();
                }
            }
        };

        final AlertDialog.Builder discardConfirmationBuilder = new AlertDialog.Builder(this, R.style.DiscardConfirmation);
        discardConfirmationBuilder.setMessage(R.string.str_discard_confirmation)
                .setPositiveButton(R.string.str_yes, discardConfirmationListener)
                .setNegativeButton(R.string.str_no, discardConfirmationListener);

        buttonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discardConfirmationBuilder.show();
            }
        });

        // TODO drawables
        switch (boardGameStatistics.getCurrentState()) {
            case BoardGameStatistics.STATE_PLAYING:
                buttonStart.setImageResource(R.drawable.delete);
                buttonDiscard.setVisibility(View.GONE);
                break;
            case BoardGameStatistics.STATE_ENDING:
                buttonStart.setImageResource(R.drawable.plus_circle);
                buttonDiscard.setVisibility(View.VISIBLE);
                break;
            default:
                buttonStart.setImageResource(R.drawable.play);
                buttonDiscard.setVisibility(View.GONE);
        }
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (boardGameStatistics.getCurrentState()) {
                    case BoardGameStatistics.STATE_PLAYING:
                        boardGameStatistics.stop();
                        buttonStart.setImageResource(R.drawable.plus_circle);
                        buttonDiscard.setVisibility(View.VISIBLE);
                        break;
                    case BoardGameStatistics.STATE_ENDING:
                        boardGameStatistics.end();
                        buttonStart.setImageResource(R.drawable.play);
                        buttonDiscard.setVisibility(View.GONE);
                        StatusService.confirmResults();
                        break;
                    default:
                        boardGameStatistics.start();
                        buttonStart.setImageResource(R.drawable.delete);
                        buttonDiscard.setVisibility(View.GONE);
                        // TODO here?
                        Intent statusService = new Intent(MainActivity.this, StatusService.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(statusService);
                        } else {
                            // TODO test this
                            startService(statusService);
                        }
                }
            }
        });


        ImageButton addPlayer = findViewById(R.id.add_player);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerSelectionActivity.start(MainActivity.this);
            }
        });

        ImageButton clearPlayerList = findViewById(R.id.clear_player_list);
        clearPlayerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardGameStatistics.clearPlayerList();
                playerList.clearPlayers();
            }
        });

        TextView gameNameView = findViewById(R.id.game_name_text);
        gameNameView.setText(boardGameStatistics.getCurrentGameName());
        gameNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameNameSelectionActivity.start(MainActivity.this);
            }
        });

        TextView locationView = findViewById(R.id.location_text);
        locationView.setText(boardGameStatistics.getCurrentLocation());
        locationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationSelectionActivity.start(MainActivity.this);
            }
        });

        View detailsDialog = getLayoutInflater().inflate(R.layout.details_dialog, null);
        ImageButton detailsDialogClear = detailsDialog.findViewById(R.id.details_clear);
        final EditText detailsDialogText = detailsDialog.findViewById(R.id.details_text);
        detailsDialogClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsDialogText.setText("");
            }
        });
        AlertDialog.Builder detailsDialogBuilder = new AlertDialog.Builder(this, R.style.DiscardConfirmation);
        detailsDialogBuilder.setView(detailsDialog);
        detailsDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO
                detailsDialogText.getText().toString();
                // TODO clear this field at the beginning of a game
            }
        });
        detailsDialogBuilder.show();

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
