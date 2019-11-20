package com.gonzalocl.boardgamestatistics.db;

import java.util.ArrayList;

public class SuggestionDB {

    private String path;
    private String name;

    ArrayList<String> tmp;

    public SuggestionDB(String path, String name) {
        this.path = path;
        this.name = name;
        // TODO check folders create if needed
        // TODO initialize file if needed



        tmp = new ArrayList<>();
    }

    public void add(String item) {
        // TODO read DB

        // TODO append item

        // TODO rewrite DB



        tmp.add(item);
    }

    public String[] getItems() {
        // TODO read DB
        String[] ret = new String[tmp.size()];
        return tmp.toArray(ret);
    }

}
