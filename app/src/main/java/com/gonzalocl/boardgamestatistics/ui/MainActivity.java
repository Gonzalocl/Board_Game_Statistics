package com.gonzalocl.boardgamestatistics.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.dynamicanimation.animation.FloatValueHolder;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.app.StatusService;
import com.gonzalocl.boardgamestatistics.app.BoardGameStatistics;

import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // TODO can this be more accurate
    private static final int DISTANCE = 200;

    // TODO bug when state ending and tap in notification
    private static FloatValueHolder playerListViewInitialX;
    private static FloatValueHolder teamListViewViewInitialX;
    private static FloatValueHolder winnersListViewViewInitialX;

    private Timer timer;

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

        final PlayerList teamList = new PlayerList(boardGameStatistics.getCurrentPlayers());
        teamList.setOnClickListener(new PlayerList.OnClickListener() {
            @Override
            public void onClick(int position) {
                // TODO select team
            }
        });

        final PlayerList winnersList = new PlayerList(boardGameStatistics.getCurrentPlayers());
        winnersList.setOnClickListener(new PlayerList.OnClickListener() {
            @Override
            public void onClick(int position) {
                // TODO set winners
            }
        });

        final RecyclerView playerListView = findViewById(R.id.player_list);
        playerListViewInitialX = new FloatValueHolder(0);
        setInitialX(playerListView, playerListViewInitialX);
        playerListView.setHasFixedSize(true);
        playerListView.setLayoutManager(new LinearLayoutManager(this));
        playerListView.setAdapter(playerList);

        final RecyclerView teamListView = findViewById(R.id.team_list);
        teamListViewViewInitialX = new FloatValueHolder(0);
        setInitialX(teamListView, teamListViewViewInitialX);
        teamListView.setHasFixedSize(true);
        teamListView.setLayoutManager(new LinearLayoutManager(this));
        teamListView.setAdapter(teamList);

        final RecyclerView winnersListView = findViewById(R.id.winners_list);
        winnersListViewViewInitialX = new FloatValueHolder(0);
        setInitialX(winnersListView, winnersListViewViewInitialX);
        winnersListView.setHasFixedSize(true);
        winnersListView.setLayoutManager(new LinearLayoutManager(this));
        winnersListView.setAdapter(winnersList);

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
                playerListView.setX(playerListView.getX()+DISTANCE);
                teamListView.setX(teamListView.getX()+DISTANCE);
                winnersListView.setX(winnersListView.getX()+DISTANCE);
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
                        stopTimer();
                        playerListView.animate().x(playerListViewInitialX.getValue()+DISTANCE);
                        teamListView.animate().x(teamListViewViewInitialX.getValue()+DISTANCE);
                        winnersListView.animate().x(winnersListViewViewInitialX.getValue()+DISTANCE);
                        break;
                    case BoardGameStatistics.STATE_ENDING:
                        boardGameStatistics.end();
                        buttonStart.setImageResource(R.drawable.play);
                        buttonDiscard.setVisibility(View.GONE);
                        ((TextView) findViewById(R.id.timer_text)).setText(R.string.timer_text_zero);
                        playerListView.animate().x(playerListViewInitialX.getValue());
                        teamListView.animate().x(teamListViewViewInitialX.getValue());
                        winnersListView.animate().x(winnersListViewViewInitialX.getValue());
                        StatusService.confirmResults();
                        break;
                    default:
                        boardGameStatistics.start();
                        buttonStart.setImageResource(R.drawable.delete);
                        buttonDiscard.setVisibility(View.GONE);
                        startTimer();
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

        ImageButton buttonDetails = findViewById(R.id.button_details);
        buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailsDialog();
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
        if (BoardGameStatistics.getBoardGameStatistics().getCurrentState() == BoardGameStatistics.STATE_PLAYING) {
            startTimer();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BoardGameStatistics.getBoardGameStatistics().getCurrentState() == BoardGameStatistics.STATE_PLAYING) {
            stopTimer();
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

    private void showDetailsDialog() {

        final BoardGameStatistics boardGameStatistics = BoardGameStatistics.getBoardGameStatistics();

        View detailsDialog = getLayoutInflater().inflate(R.layout.details_dialog, null);
        ImageButton detailsDialogClear = detailsDialog.findViewById(R.id.details_clear);
        final EditText detailsDialogText = detailsDialog.findViewById(R.id.details_text);
        AlertDialog.Builder detailsDialogBuilder = new AlertDialog.Builder(this, R.style.DiscardConfirmation);

        detailsDialogClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsDialogText.setText("");
            }
        });

        detailsDialogText.setText(boardGameStatistics.getCurrentDetails());

        detailsDialogBuilder.setView(detailsDialog);
        detailsDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                boardGameStatistics.setCurrentDetails(detailsDialogText.getText().toString());
            }
        });

        detailsDialogBuilder.show();

    }

    private void startTimer() {

        final TextView timerView = findViewById(R.id.timer_text);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                long now = new Date().getTime()/1000;
                long startTime = BoardGameStatistics.getBoardGameStatistics().getStartTime()/1000;
                long elapsed = now-startTime;

                long hours = elapsed/3600%60;
                long minutes = elapsed/60%60;
                long seconds = elapsed%60;

                final String timerText = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerView.setText(timerText);
                    }
                });

            }
        };

        timer = new Timer();
        timer.schedule(task, 0, 1000);

    }

    private void stopTimer() {
        timer.cancel();
    }

    private void setInitialX(final View view, final FloatValueHolder initialX) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initialX.setValue(view.getX());
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

}
