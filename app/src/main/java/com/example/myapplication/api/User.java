package com.example.myapplication.api;

import lombok.Data;

@Data
public class User {
    private int id;
    private String nick;
    private String password;
    private int score;
}