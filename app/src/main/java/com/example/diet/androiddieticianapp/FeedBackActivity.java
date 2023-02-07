package com.example.diet.androiddieticianapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

public class FeedBackActivity extends AppCompatActivity {

    ListView userListView;
    FeedBack feedBack;
    DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_activity);
        //feedBack = (FeedBack) getIntent().getExtras().getSerializable("feedback");

        dbHelper = new DbHelper(this);

        userListView = (ListView) findViewById(R.id.userListView);

        FeedBackListAdapter feedBackListAdapter = new FeedBackListAdapter(FeedBackActivity.this, dbHelper.getFeedbacks());
        userListView.setAdapter(feedBackListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FeedBackListAdapter feedBackListAdapter = new FeedBackListAdapter(FeedBackActivity.this, dbHelper.getFeedbacks());
        userListView.setAdapter(feedBackListAdapter);
    }
}
