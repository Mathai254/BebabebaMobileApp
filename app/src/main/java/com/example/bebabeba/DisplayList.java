package com.example.bebabeba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import org.json.JSONException;

import java.util.ArrayList;

public class DisplayList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Contact> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*
        Intent intent = getIntent();
        final String v_type = intent.getStringExtra("v_type");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        BackgroundTask backgroundTask = new BackgroundTask(DisplayList.this);
        try {
            arrayList = backgroundTask.getList(v_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(adapter);*/
    }
}
