package com.example.diet.androiddieticianapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendResponse extends AppCompatActivity {

    DbHelper dbHelper;
    FeedBack feedBack;
    User user;
    TextView feedbackmessage, useremail;
    EditText responsemessage;
    Button btnSend, btnCancel;
    String userEmail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_response);
        dbHelper = new DbHelper(this);
        feedBack=(FeedBack) getIntent().getExtras().getSerializable("feedback");
        useremail=(TextView) findViewById(R.id.useremail);
        feedbackmessage=(TextView) findViewById(R.id.feedbacktext);
        responsemessage=(EditText) findViewById(R.id.editresponsetext);
        btnSend=(Button) findViewById(R.id.button3);
        btnCancel =(Button) findViewById(R.id.button2);
        feedbackmessage.setText(feedBack.getMessage());
        Intent intent=getIntent();
        useremail.setText("User Email:"+intent.getStringExtra( "email"));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if(responsemessage.getText().toString().trim().isEmpty()){
                 responsemessage.setError("Cannot be empty");
             }else {
                 Response response=new Response();
                 response.setId(String.valueOf(System.currentTimeMillis()));
                 response.setEmail("dietician@gmail.com");
                 response.setResponsemessage(responsemessage.getText().toString());
                 response.setUserEmail(feedBack.getEmail());
                 dbHelper.addResponse(response);
                 Toast.makeText(getApplicationContext(),"Response sent successfully",Toast.LENGTH_SHORT).show();
                 SendResponse.super.onBackPressed();
             }
             }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendResponse.super.onBackPressed();
            }
        });


    }
}
