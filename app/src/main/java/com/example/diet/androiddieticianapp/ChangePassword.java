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

public class ChangePassword extends AppCompatActivity {
    TextView Email;
    EditText Password,Confirmpassword;
    Button ChangePassword;
    DbHelper dbHelper;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        dbHelper=new DbHelper(this);
        Email=(TextView)findViewById(R.id.useremail);
        Password=(EditText) findViewById(R.id.password);
        Confirmpassword=(EditText) findViewById(R.id.confirmpassword);
        ChangePassword=(Button) findViewById(R.id.btnChangePassword);
        Intent intent=getIntent();
        Email.setText(intent.getStringExtra( "email"));
        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=Email.getText().toString();
                String pass=Password.getText().toString().trim();
                String confirmpass=Confirmpassword.getText().toString();


                if(pass.trim().equals(confirmpass)){
                    dbHelper.updatePassword(email,pass);
                    Toast.makeText(getApplicationContext(),"Password changed successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangePassword.this,LoginActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
