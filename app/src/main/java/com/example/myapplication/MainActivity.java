package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.api.DataController;
import com.example.myapplication.api.User;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int numberToGuess;
    private int numberOfTrials;
    private boolean onRun;
    private int bestScore;
    private Random random = new Random();

    private DataController dataController;

    private User loggedUser;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dataController = new DataController();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        TextView nick = findViewById(R.id.nick_text);
        TextView password = findViewById(R.id.password_text);


        Button login = findViewById(R.id.button);
        Button register = findViewById(R.id.register_button);

        login.setOnClickListener(event -> {
            new Thread(() -> {
                try {
                    loggedUser = dataController.getScore(nick.getText()
                                                             .toString(),
                                                         password.getText()
                                                                 .toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(this::newGame);
            }).start();
        });

        register.setOnClickListener(event -> {
            new Thread(() ->
                       {
                           try {
                               loggedUser = dataController.saveUser(nick.getText()
                                                                        .toString(),
                                                                    password.getText()
                                                                            .toString());
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                           runOnUiThread(this::newGame);
                       }).start();
        });


    }

    private void initVariablesForGame() {
        numberToGuess = Math.abs(random.nextInt() % 21);
        numberOfTrials = 0;
        bestScore = Integer.MAX_VALUE;
        onRun = true;
    }

    protected void newGame() {
        setContentView(R.layout.activity_main);
        initVariablesForGame();

        Button button = findViewById(R.id.button);
        final TextView changing = findViewById(R.id.changing_text);
        final TextView trialsNumber = findViewById(R.id.number_of_trails);


        button.setOnClickListener(view -> {
            if (onRun) {
                EditText editTextNumber = findViewById(R.id.password_text);
                int number = Integer.parseInt(editTextNumber.getText()
                                                            .toString());
                numberOfTrials += 1;
                if (number > 20 || number < 0) {
                    numberOfTrials -= 1;
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Your number must be in between 0 and 20");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                                          "OK",
                                          new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface,
                                                                  int i) {
                                                  dialogInterface.dismiss();
                                              }
                                          });
                    alertDialog.show();
                }
                if (number == numberToGuess) {
                    if (numberOfTrials < bestScore) {
                        bestScore = numberOfTrials;
                    }
                    String message = "Best score: " + bestScore;
                    changing.setText(message);
                    loggedUser.setScore(numberOfTrials);
                    int finalTrials = numberOfTrials;
                    new Thread(() -> {
                                   try {
                                       dataController.saveScore(loggedUser.getNick(),
                                                                String.valueOf(finalTrials));
                                   } catch (IOException e) {
                                       e.printStackTrace();
                                   }
                               }).start();
                    startActivity(new Intent(this, ShowRecordsActivity.class));
                    initVariablesForGame();
                } else {
                    String message = "Number is ";
                    if (number > numberToGuess) {
                        message += "lower";
                    } else {
                        message += "higher";
                    }
                    changing.setText(message);
                }
                String message = "Number of trials: " + numberOfTrials;
                trialsNumber.setText(message);
            }
        });
    }
}
