package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SaveScoreActivity extends AppCompatActivity {

    private static final String TAG = "SaveScoreActivity";

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_score);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        System.out.println("hello");
        mDatabaseHelper = new DatabaseHelper(this);
        System.out.println("hello");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hello");
                onButtonClicked();
            }
        });
    }

    protected void onButtonClicked() {
        TextView scoreView = (TextView) findViewById(R.id.scoreView);
        TextView enterName = (TextView) findViewById(R.id.password_text);
        boolean insertData = mDatabaseHelper.addData(enterName.getText().toString(), scoreView.getText().toString());

        if (insertData) {
            toastMessage("Score saved");
        } else {
            toastMessage("Couldn't save your score");
        }
        startActivity(new Intent(SaveScoreActivity.this, ShowRecordsActivity.class));
        finish();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
