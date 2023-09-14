package com.trello.secrets;

public class TrelloSecrets {
    private TrelloSecrets() {
    }

    private static final String KEY = "mykey";
    private static final String TOKEN = "mytoken";

    public static String getKey() {
        return KEY;
    }

    public static String getToken() {
        return TOKEN;
    }
}
