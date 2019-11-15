package com.gonzalocl.boardgamestatistics.db;

import java.util.Map;

public class DBEntry {

    private String gameName;
    private String location;
    private int startTime;
    private int endTime;
    private Map<String, String[]> players;
    private Map<Integer, java.util.Map<String, String[]>> winners;
    private String details;

    public DBEntry() {
        // TODO initialize maps
    }

    public String getGameName() {
        return gameName;
    }

    public DBEntry setGameName(String gameName) {
        this.gameName = gameName;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public DBEntry setLocation(String location) {
        this.location = location;
        return this;
    }

    public int getStartTime() {
        return startTime;
    }

    public DBEntry setStartTime(int startTime) {
        this.startTime = startTime;
        return this;
    }

    public int getEndTime() {
        return endTime;
    }

    public DBEntry setEndTime(int endTime) {
        this.endTime = endTime;
        return this;
    }

    // TODO
//    public Map<String, String[]> getPlayers() {
//        return players;
//    }

    public DBEntry setPlayers(String team, String[] players) {
        // TODO
        return this;
    }

    // TODO
//    public Map<Integer, Map<String, String[]>> getWinners() {
//        return winners;
//    }
//
//    public DBEntry setWinners(Map<Integer, Map<String, String[]>> winners) {
//        this.winners = winners;
//        return this;
//    }

    public String getDetails() {
        return details;
    }

    public DBEntry setDetails(String details) {
        this.details = details;
        return this;
    }
}
