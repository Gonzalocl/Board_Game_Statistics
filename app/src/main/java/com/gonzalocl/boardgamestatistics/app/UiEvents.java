package com.gonzalocl.boardgamestatistics.app;

import com.gonzalocl.boardgamestatistics.db.SuggestionDB;

public class UiEvents {

    public static final int SELECTION_TYPE_PLAYER = 0;
    public static final int SELECTION_TYPE_GAME = 1;
    public static final int SELECTION_TYPE_LOCATION = 2;
    public static final int SELECTION_TYPE_TEAM = 3;

    private static final UiEvents uiEvents = new UiEvents();

    private UiEvents() {

    }

    public static UiEvents getUiEvents() {
        return uiEvents;
    }

    public void start() {

    }

    public void stop() {

    }

    public void addPlayer(int i) {

    }

    public void deletePlayer(int i) {

    }

    public void clearPlayerList() {

    }

    public void setGameName(int i) {

    }

    public void setLocation(int i) {

    }

    static SuggestionDB sdb = new SuggestionDB("", "");

    public void newPlayer(String player) {
        sdb.add(player);
    }

    public String[] getSuggestedPlayers() {
        return sdb.getItems();
    }

    public String[] getSuggestedGameNames() {
        return null;
    }

    public String[] getSuggestedLocations() {
        return null;
    }

    public String[] getSuggestedTeams() {
        return null;
    }

}
