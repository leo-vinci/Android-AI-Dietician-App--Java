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

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    TextView tvRegister, tvForgetPassword;
    DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DbHelper(this);

        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_password);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvForgetPassword=(TextView) findViewById(R.id.txtforgetpassword);
        login = (Button) findViewById(R.id.button_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().isEmpty()) {
                    email.setError("Email required");
                } else if (password.getText().toString().trim().isEmpty()) {
                    password.setError("Password required");
                } else if (email.getText().toString().equals("admin") &&
                        password.getText().toString().equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                else if (email.getText().toString().equals("dietician@gmail.com") &&
                        password.getText().toString().equals("diet123")) {
                    Intent intent = new Intent(LoginActivity.this, DieticianActivity.class);
                    startActivity(intent);
                }else {
                    User user = dbHelper.getUser(email.getText().toString(), password.getText().toString());
                    if (user != null) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user", (Serializable) user);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });

    }
}
