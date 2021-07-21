package com.example.d_bee_5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerView extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<String> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = findViewById(R.id.recyclerView);
        initData();
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(data));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    void initData(){
        for (int i = 0; i < 50; i++) {
            StringBuilder singleText = new StringBuilder();
            for (int j = 0; j < (int)(Math.random()*100)+1; j++) {
                singleText.append("测试文本");
            }
            data.add(singleText.toString());
        }
    }
}