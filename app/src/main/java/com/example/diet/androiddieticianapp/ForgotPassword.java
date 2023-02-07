package com.example.diet.androiddieticianapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
    EditText Email;
    Button Reset;
    DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        dbHelper=new DbHelper(this);

        Email=(EditText) findViewById(R.id.edtconfirmemail);
        Reset =(Button) findViewById(R.id.btnConfirmEmail);

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ConfirmEmail= Email.getText().toString();
                if(dbHelper.isUserAlreadyExist(ConfirmEmail.trim())){
                    Toast.makeText(getApplicationContext(),"This email does  exist",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ForgotPassword.this,ChangePassword.class);
                    intent.putExtra("email",ConfirmEmail);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(),"This email does not exist",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
