package com.gonzalocl.boardgamestatistics.ui;

import android.content.Context;
import android.content.Intent;

import com.gonzalocl.boardgamestatistics.app.BoardGameStatistics;

public class LocationSelectionActivity extends SelectionActivity {

    static void start(Context context) {
        Intent intent = new Intent(context, LocationSelectionActivity.class);
        context.startActivity(intent);
    }

    //TODO set strings

    @Override
    public String[] getSuggestions() {
        return BoardGameStatistics.getBoardGameStatistics().getSuggestedLocations();
    }

    @Override
    public void onItemSelected(int position) {
        BoardGameStatistics.getBoardGameStatistics().setCurrentLocation(position);
    }

    @Override
    public void onAddNewItem(String item) {
        BoardGameStatistics.getBoardGameStatistics().newLocation(item);
    }

}
