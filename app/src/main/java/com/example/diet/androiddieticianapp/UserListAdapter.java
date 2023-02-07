package com.example.diet.androiddieticianapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {

    Context theActivity;
    ArrayList<User> fd = new ArrayList<User>();

    public UserListAdapter(Context ctx, ArrayList<User> list) {
        theActivity = ctx;
        fd = list;
    }

    @Override
    public int getCount() {

        return fd.size();
    }

    @Override
    public Object getItem(int i) {

        return null;
    }

    @Override
    public long getItemId(int i) {

        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        View newView = null;

        User user = fd.get(pos);

        LayoutInflater inflater = LayoutInflater.from(theActivity);


        newView = inflater.inflate(R.layout.users_row, null, true);
        TextView nameText = (TextView) newView.findViewById(R.id.tv_email);
        LinearLayout linRow = (LinearLayout) newView.findViewById(R.id.lin_row);
        linRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(theActivity, UserModify.class);
                intent.putExtra("user", (Serializable) user);
                theActivity.startActivity(intent);
            }
        });
        nameText.setText(user.getEmail());
        return newView;

    }

}


