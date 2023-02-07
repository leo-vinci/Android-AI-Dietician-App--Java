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

public class AdminActivity extends AppCompatActivity {

    ListView userListView;

    DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);


        dbHelper = new DbHelper(this);

        userListView = (ListView) findViewById(R.id.userListView);

        UserListAdapter customAdapter = new UserListAdapter(AdminActivity.this, dbHelper.getUserList());
        userListView.setAdapter(customAdapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(AdminActivity.this, UserModify.class));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.foods:{
                startActivity(new Intent(AdminActivity.this, AddFoodActivity.class));
                return true;
            }
            case R.id.viewFood:{
                startActivity(new Intent(AdminActivity.this,ViewFoodActivity.class));
                return true;
            }

            case R.id.log_out: {
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                finish();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        UserListAdapter customAdapter = new UserListAdapter(AdminActivity.this, dbHelper.getUserList());
        userListView.setAdapter(customAdapter);
    }


}



