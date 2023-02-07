package com.example.diet.androiddieticianapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diet.androiddieticianapp.ResponseListAdapter;

import org.w3c.dom.Text;

import java.io.Serializable;

public class ResponseActivity extends AppCompatActivity {

    ListView responseListView;
    Response response;
    DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_reponseactivity);
        dbHelper =new DbHelper(this);
        responseListView=(ListView) findViewById(R.id.userListView);

        ResponseListAdapter responseListAdapter=new ResponseListAdapter(ResponseActivity.this, dbHelper.getResponse(getEmail()));
        responseListView.setAdapter(responseListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ResponseListAdapter responseListAdapter=new ResponseListAdapter(ResponseActivity.this, dbHelper.getResponse(getEmail()));
        responseListView.setAdapter(responseListAdapter);
    }

    public String getEmail() {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        return sharedPref.getString("email", "");
    }
}


