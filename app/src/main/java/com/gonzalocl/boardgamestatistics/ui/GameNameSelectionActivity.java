package com.gonzalocl.boardgamestatistics.ui;

import android.content.Context;
import android.content.Intent;

import com.gonzalocl.boardgamestatistics.app.BoardGameStatistics;

public class GameNameSelectionActivity extends SelectionActivity {

    static void start(Context context) {
        Intent intent = new Intent(context, GameNameSelectionActivity.class);
        context.startActivity(intent);
    }

    //TODO set strings

    @Override
    public String[] getSuggestions() {
        return BoardGameStatistics.getBoardGameStatistics().getSuggestedGameNames();
    }

    @Override
    public void onItemSelected(int position) {
        BoardGameStatistics.getBoardGameStatistics().setCurrentGameName(position);
    }

    @Override
    public void onAddNewItem(String item) {
        BoardGameStatistics.getBoardGameStatistics().newGameName(item);
    }

}
