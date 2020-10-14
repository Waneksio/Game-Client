package com.example.myapplication.api;

import java.io.IOException;
import java.util.List;

public class DataController extends BasicController {
    private AccessDataService accessDataService;

    public DataController() {
        super();
        accessDataService = retrofit.create(AccessDataService.class);
    }

    public User getScore(String nick, String password) throws IOException {
        return accessDataService.getScore(nick, password).execute().body();
    }

    public List<User> getScores() throws IOException {
        return accessDataService.getScores().execute().body();
    }

    public User saveScore(String nick, String score) throws IOException {
        return accessDataService.saveScore(nick, score).execute().body();
    }


    public User saveUser(String nick, String password) throws IOException {
        return accessDataService.saveUser(nick, password).execute().body();
    }
}
