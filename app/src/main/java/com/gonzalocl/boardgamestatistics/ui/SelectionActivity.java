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

public class SelectionActivity extends AppCompatActivity {


    static void start(Context context) {
        Intent intent = new Intent(context, SelectionActivity.class);
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

        setTitle(getSelectionTitle());
        ((EditText) findViewById(R.id.selection_search)).setHint(getSearchHint());
        ((EditText) findViewById(R.id.new_item_name)).setHint(getNewItemNameHint());
        SuggestionsList suggestionsList = new SuggestionsList(getSuggestions());
        suggestionsList.setOnClickListener(new SuggestionsList.OnClickListener() {
            @Override
            public void onClick(int position) {
                onItemSelected(position);
                MainActivity.start(SelectionActivity.this);
            }
        });

        RecyclerView suggestionsListView = findViewById(R.id.selection_list);
        suggestionsListView.setHasFixedSize(true);
        suggestionsListView.setLayoutManager(new LinearLayoutManager(this));
        suggestionsListView.setAdapter(suggestionsList);


        Button newItem = findViewById(R.id.new_item_add);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newItemName = findViewById(R.id.new_item_name);
                onAddNewItem(newItemName.getText().toString());
                MainActivity.start(SelectionActivity.this);
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

    public int getSelectionTitle(){
        return R.string.app_name;
    }

    public int getSearchHint() {
        return R.string.selection_search;
    }

    public int getNewItemNameHint() {
        return R.string.new_item_name;
    }

    public String[] getSuggestions() {
        return new String[0];
    }

    public void onItemSelected(int position) {

    }

    public void onAddNewItem(String item) {

    }

}
