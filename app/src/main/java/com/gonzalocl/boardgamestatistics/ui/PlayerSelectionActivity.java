package com.gonzalocl.boardgamestatistics.ui;

import android.content.Context;
import android.content.Intent;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.app.BoardGameStatistics;

public class PlayerSelectionActivity extends SelectionActivity {

    static void start(Context context) {
        Intent intent = new Intent(context, PlayerSelectionActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getSelectionTitle() {
        return R.string.selection_player_title;
    }

    @Override
    public int getSearchHint() {
        return R.string.selection_player_search;
    }

    @Override
    public int getNewItemNameHint() {
        return R.string.selection_player_add;
    }

    @Override
    public String[] getSuggestions() {
        return BoardGameStatistics.getBoardGameStatistics().getSuggestedPlayers();
    }

    @Override
    public void onItemSelected(int position) {
        BoardGameStatistics.getBoardGameStatistics().addPlayer(position);
    }

    @Override
    public void onAddNewItem(String item) {
        BoardGameStatistics.getBoardGameStatistics().newPlayer(item);
    }

}
