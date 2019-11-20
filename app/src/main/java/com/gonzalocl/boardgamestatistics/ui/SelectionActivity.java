package com.gonzalocl.boardgamestatistics.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.app.UiEvents;

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
        setContentView(R.layout.activity_selection);

        Intent intent = getIntent();
        int selectionType = intent.getIntExtra(KEY_SELECTION_TYPE, -1);
        SuggestionsList suggestionsList;
        UiEvents uiEvents = UiEvents.getUiEvents();

        if (selectionType == UiEvents.SELECTION_TYPE_PLAYER) {
            setTitle(R.string.selection_player_title);
            ((EditText)findViewById(R.id.selection_search)).setHint(R.string.selection_player_search);
            ((EditText)findViewById(R.id.new_item_name)).setHint(R.string.selection_player_add);
            suggestionsList = new SuggestionsList(uiEvents.getSuggestedPlayers());
            suggestionsList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UiEvents.getUiEvents().newPlayer(((TextView)v).getText().toString());
                }
            });
        } else if (selectionType == UiEvents.SELECTION_TYPE_GAME) {
            // TODO set strings
            suggestionsList = new SuggestionsList(uiEvents.getSuggestedGameNames());
        } else if (selectionType == UiEvents.SELECTION_TYPE_LOCATION) {
            // TODO set strings
            suggestionsList = new SuggestionsList(uiEvents.getSuggestedLocations());
        } else if (selectionType == UiEvents.SELECTION_TYPE_TEAM) {
            // TODO set strings
            suggestionsList = new SuggestionsList(uiEvents.getSuggestedTeams());
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
                UiEvents.getUiEvents().newPlayer(newItemName.getText().toString());
            }
        });


    }
}
