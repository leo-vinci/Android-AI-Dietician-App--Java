package com.example.diet.androiddieticianapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;

public class ViewFoodActivity extends AppCompatActivity {
    ListView userListView,userListView1,userListView2;
    DbHelper dbHelper;
    Food food;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showfoodlist);

        dbHelper=new DbHelper(this);
        userListView=(ListView) findViewById(R.id.userListView);

        ViewFoodListAdapter vegListAdapter=new ViewFoodListAdapter(ViewFoodActivity.this,dbHelper.getAllFoods());
        userListView.setAdapter(vegListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewFoodListAdapter vegListAdapter=new ViewFoodListAdapter(ViewFoodActivity.this,dbHelper.getAllFoods());
        userListView.setAdapter(vegListAdapter);
    }

}
