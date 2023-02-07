package com.example.diet.androiddieticianapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DieticianActivity extends AppCompatActivity {

    ListView userListView;
    DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);

        dbHelper = new DbHelper(this);

        userListView = (ListView) findViewById(R.id.userListView);

        UserListAdapter customAdapter = new UserListAdapter(DieticianActivity.this, dbHelper.getUserList());
        userListView.setAdapter(customAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dieticianmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.foods: {
                startActivity(new Intent(DieticianActivity.this, AddFoodActivity.class));
                return true;
            }
            case R.id.viewFood:{
                startActivity(new Intent(DieticianActivity.this,ViewFoodActivity.class));
                return true;
            }
            case R.id.log_out: {
                startActivity(new Intent(DieticianActivity.this, LoginActivity.class));
                finish();
                return true;
            }

            case R.id.feedback: {
                startActivity(new Intent(DieticianActivity.this, FeedBackActivity.class));
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        UserListAdapter customAdapter = new UserListAdapter(DieticianActivity.this, dbHelper.getUserList());
        userListView.setAdapter(customAdapter);
    }
}
