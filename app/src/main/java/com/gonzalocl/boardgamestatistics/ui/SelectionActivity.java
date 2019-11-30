package com.gonzalocl.boardgamestatistics.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.app.BoardGameStatistics;

public class SelectionActivity extends AppCompatActivity {

    private static final String KEY_SELECTION_TYPE = "selection_type";

    static void start(Context context, int type) {
        Intent intent = new Intent(context, SelectionActivity.class);
        intent.putExtra(KEY_SELECTION_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = prefs.getBoolean(SettingsActivity.preferences_theme_key, false);
        if(darkTheme) setTheme(R.style.DarkTheme);
        else setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_selection);

        Intent intent = getIntent();
        int selectionType = intent.getIntExtra(KEY_SELECTION_TYPE, -1);
        SuggestionsList suggestionsList;
        final BoardGameStatistics boardGameStatistics = BoardGameStatistics.getBoardGameStatistics();

        if (selectionType == BoardGameStatistics.SELECTION_TYPE_PLAYER) {
            setTitle(R.string.selection_player_title);
            ((EditText)findViewById(R.id.selection_search)).setHint(R.string.selection_player_search);
            ((EditText)findViewById(R.id.new_item_name)).setHint(R.string.selection_player_add);
            suggestionsList = new SuggestionsList(boardGameStatistics.getSuggestedPlayers());
            suggestionsList.setOnClickListener(new SuggestionsList.OnClickListener() {
                @Override
                public void onClick(int position) {
                    boardGameStatistics.addPlayer(position);
                    MainActivity.star(SelectionActivity.this);
                }
            });
        } else if (selectionType == BoardGameStatistics.SELECTION_TYPE_GAME) {
            // TODO set strings
            suggestionsList = new SuggestionsList(boardGameStatistics.getSuggestedGameNames());
        } else if (selectionType == BoardGameStatistics.SELECTION_TYPE_LOCATION) {
            // TODO set strings
            suggestionsList = new SuggestionsList(boardGameStatistics.getSuggestedLocations());
        } else if (selectionType == BoardGameStatistics.SELECTION_TYPE_TEAM) {
            // TODO set strings
            suggestionsList = new SuggestionsList(boardGameStatistics.getSuggestedTeams());
        } else {
            suggestionsList = new SuggestionsList(new String[0]);
        }

        RecyclerView suggestionsListView = findViewById(R.id.selection_list);
        suggestionsListView.setHasFixedSize(true);
        suggestionsListView.setLayoutManager(new LinearLayoutManager(this));
        suggestionsListView.setAdapter(suggestionsList);


        Button newItem = findViewById(R.id.new_item_add);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newItemName = findViewById(R.id.new_item_name);
                BoardGameStatistics.getBoardGameStatistics().newPlayer(newItemName.getText().toString());
                MainActivity.star(SelectionActivity.this);
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
}
