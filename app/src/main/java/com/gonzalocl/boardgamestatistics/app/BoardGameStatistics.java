package com.gonzalocl.boardgamestatistics.app;

import com.gonzalocl.boardgamestatistics.db.SuggestionDB;

import java.util.ArrayList;
import java.util.Date;

public class BoardGameStatistics {

    public static final int SELECTION_TYPE_PLAYER = 0;
    public static final int SELECTION_TYPE_GAME = 1;
    public static final int SELECTION_TYPE_LOCATION = 2;
    public static final int SELECTION_TYPE_TEAM = 3;

    private static final String UNDEFINED_GAME_NAME = "Undefined game name";
    private static final String UNDEFINED_LOCATION = "Undefined location";

    public static final int STATE_NO_GAME = 1;
    public static final int STATE_PLAYING = 2;
    public static final int STATE_ENDING = 3;

    private static final BoardGameStatistics boardGameStatistics = new BoardGameStatistics();

    private int currentState;

    private String currentGameName;
    private String currentLocation;
    private ArrayList<String> currentPlayers;
    // TODO currentTeams

    private long startTime;
    private long endTime;

    private SuggestionDB gameNameSuggestions;
    private SuggestionDB locationSuggestions;
    private SuggestionDB playerSuggestions;
    private SuggestionDB teamSuggestions;

    private String[] activeGameNameSuggestions;
    private String[] activeLocationSuggestions;
    private String[] activePlayerSuggestions;
    private String[] activeTeamSuggestions;


    private BoardGameStatistics() {

        currentState = STATE_NO_GAME;

        currentGameName = UNDEFINED_GAME_NAME;
        currentLocation = UNDEFINED_LOCATION;
        currentPlayers = new ArrayList<>();
        // TODO currentTeams

        gameNameSuggestions = new SuggestionDB("", "");
        locationSuggestions = new SuggestionDB("", "");
        playerSuggestions = new SuggestionDB("", "");
        teamSuggestions = new SuggestionDB("", "");
    }

    public static BoardGameStatistics getBoardGameStatistics() {
        return boardGameStatistics;
    }


    public void start() {
        currentState = STATE_PLAYING;
        startTime = new Date().getTime();
    }

    public void stop() {
        currentState = STATE_ENDING;
        endTime = new Date().getTime();
    }

    public void end() {
        currentState = STATE_NO_GAME;
    }

    public void discard() {
        currentState = STATE_NO_GAME;
    }


    public void setCurrentGameName(int i) {
        currentGameName = activeGameNameSuggestions[i];
    }

    public void setCurrentLocation(int i) {
        currentLocation = activeLocationSuggestions[i];
    }

    public void addPlayer(int i) {
        currentPlayers.add(activePlayerSuggestions[i]);
    }

    public void addTeam(int player, int team) {
        // TODO activeTeamSuggestions[team];
    }


    public int getCurrentState() {
        return currentState;
    }

    public String getCurrentGameName() {
        return currentGameName;
    }


    public String getCurrentLocation() {
        return currentLocation;
    }

    public String[] getCurrentPlayers() {
        String[] ret = new String[currentPlayers.size()];
        return currentPlayers.toArray(ret);
    }

//    public getCurrentTeams()

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }


    public void deletePlayer(int i) {
        currentPlayers.remove(i);
    }

    public void clearPlayerList() {
        currentPlayers = new ArrayList<>();
    }








    public void newGameName(String gameName) {
        gameNameSuggestions.add(gameName);
        currentGameName = gameName;
    }

    public void newLocation(String location) {
        locationSuggestions.add(location);
        currentLocation = location;
    }

    public void newPlayer(String player) {
        playerSuggestions.add(player);
        currentPlayers.add(player);
    }

    public void newTeam(String team) {
        teamSuggestions.add(team);
        // TODO currentTeams
    }


    public String[] getSuggestedGameNames() {
        activeGameNameSuggestions = gameNameSuggestions.getItems();
        return gameNameSuggestions.getItems();
    }

    public String[] getSuggestedLocations() {
        activeLocationSuggestions = locationSuggestions.getItems();
        return locationSuggestions.getItems();
    }

    public String[] getSuggestedPlayers() {
        activePlayerSuggestions = playerSuggestions.getItems();
        return playerSuggestions.getItems();
    }

    public String[] getSuggestedTeams() {
        activeTeamSuggestions = teamSuggestions.getItems();
        return teamSuggestions.getItems();
    }

}
