package com.example.myapplication.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private int id;
    private String nick;
    private String password;
    private int score;
}
