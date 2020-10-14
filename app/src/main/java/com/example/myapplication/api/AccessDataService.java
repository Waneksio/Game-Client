package com.example.myapplication.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccessDataService {
    @POST(value = "/user")
    Call<User> saveUser(@Query("nick") String nick, @Query("password") String password);

    @POST(value = "/user/score")
    Call<User> saveScore(@Query("nick") String nick, @Query("score") String score);

    @GET(value = "/user/scores")
    Call<List<User>> getScores();

    @GET(value = "/user/score")
    Call<User> getScore(@Query("nick") String nick, @Query("password") String password);


}
