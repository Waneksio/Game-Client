package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.DataController;
import com.example.myapplication.api.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public class ShowRecordsActivity extends AppCompatActivity {

    private DataController dataController = new DataController();
    private ListView mListView;

    @SneakyThrows
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        mListView = (ListView) findViewById(R.id.listView);

        Button finishButton = findViewById(R.id.finishbutton);
        finishButton.setOnClickListener(event -> {
            finish();
        });

        populateListView();
    }

    private void populateListView() throws
                                    InterruptedException {
        AtomicReference<List<User>> listData = new AtomicReference<>();

        Thread myThread = new Thread(() -> {
            try {
                listData.set(dataController.getScores());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        myThread.start();
        myThread.join();

        ListAdapter adapter = new ArrayAdapter<>(this,
                                                 android.R.layout.simple_list_item_1,
                                                 listData.get()
                                                         .stream()
                                                         .map(o -> o.getNick() + ": " + o.getScore())
                                                         .collect(Collectors.toList()));
        mListView.setAdapter(adapter);
    }
}
