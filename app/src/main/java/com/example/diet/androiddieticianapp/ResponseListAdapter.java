package com.example.diet.androiddieticianapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.diet.androiddieticianapp.FeedBack;
import com.example.diet.androiddieticianapp.R;
import com.example.diet.androiddieticianapp.Response;
import com.example.diet.androiddieticianapp.ResponseActivity;
import com.example.diet.androiddieticianapp.SendResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseListAdapter extends BaseAdapter {

    Context theActivity;
    ArrayList<Response> fd = new ArrayList<Response>();

    public ResponseListAdapter(Context ctx, ArrayList<Response> list) {
        theActivity = ctx;
        fd = list;
    }

    @Override
    public int getCount() {
        return fd.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        View newView = null;

        Response response = fd.get(pos);

        LayoutInflater inflater = LayoutInflater.from(theActivity);
        newView = inflater.inflate(R.layout.feedbackreponse_row, null, true);
        LinearLayout linRow = (LinearLayout) newView.findViewById(R.id.lin_row);
        TextView nameText = (TextView) newView.findViewById(R.id.tv_emailresponse);
        TextView responsetext = (TextView) newView.findViewById(R.id.tv_feedbackresponse);
        linRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(theActivity, ResponseActivity.class);
//                theActivity.startActivity(intent);
            }
        });
        nameText.setText(response.getEmail());
        responsetext.setText(response.getResponsemessage());
        return newView;


    }
}
