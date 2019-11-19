package com.gonzalocl.boardgamestatistics.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        if (selectionType == UiEvents.SELECTION_TYPE_PLAYER) {
            setTitle(R.string.selection_player_title);
            ((EditText)findViewById(R.id.selection_search)).setHint(R.string.selection_player_search);
            ((EditText)findViewById(R.id.new_item_name)).setHint(R.string.selection_player_add);
        } else if (selectionType == UiEvents.SELECTION_TYPE_GAME) {
            // TODO set strings
        } else if (selectionType == UiEvents.SELECTION_TYPE_LOCATION) {
            // TODO set strings
        } else if (selectionType == UiEvents.SELECTION_TYPE_TEAM) {
            // TODO set strings
        }



    }
}
