package com.example.diet.androiddieticianapp;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText eWeight, eHeight, eInches, eAge, activityrate, email, password,name,mobilenumber;
    RadioButton rmale, rfemale;
    Button register;
    DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DbHelper(RegisterActivity.this);
        //name=(EditText) findViewById(R.id.edt_name);
        //mobilenumber=(EditText) findViewById(R.id.edt_mobilenumber);
        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_password);
        eWeight = (EditText) findViewById(R.id.edt_weight);
        eHeight = (EditText) findViewById(R.id.edt_height);
        eInches = (EditText) findViewById(R.id.edt_inches);
        eAge = (EditText) findViewById(R.id.edt_age);
        activityrate = (EditText) findViewById(R.id.edt_activity);
        rmale = (RadioButton) findViewById(R.id.radioButton);
        rfemale = (RadioButton) findViewById(R.id.radioButton1);
        register = (Button) findViewById(R.id.btn_register);
        rfemale.setChecked(true);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email=email.getText().toString();
                //String Name=name.getText().toString();
                //String Mobile=mobilenumber.getText().toString();
                String Password=password.getText().toString();
                String Rate = (activityrate.getText().toString());
                String Age=(eAge.getText().toString());
                String Weight=((eWeight.getText().toString()));
                String Height=(eHeight.getText().toString());
                String Inches=(eInches.getText().toString());

                /*if (Name.trim().isEmpty()){
                    name.setError("Cannot be empty");
                }
                else if(Mobile.trim().isEmpty()){
                    mobilenumber.setError("Mobile number required");
                }*/
                 if (Email.trim().isEmpty()) {
                    email.setError("Email required");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Invalid Email Address");
                } else if (Password.trim().isEmpty()) {
                    password.setError("Invalid Password");
                } else if (Weight.trim().isEmpty()) {
                    eWeight.setError("Weight required");
                }
                else if(Float.parseFloat(Weight)>300 || Float.parseFloat(Weight)==0){
                    eWeight.setError("Weight cannot be 0 or greater than 300 kg");
                }
                else if (Height.trim().isEmpty()) {
                    eHeight.setError("Height in ft required");
                }
                else if(Integer.parseInt(Height)>8 || Integer.parseInt(Height)==0){
                    eHeight.setError("Feet cannot be  0 or greater than 8");
                }
                else if (Inches.trim().isEmpty()) {
                    eInches.setError("Inches required");
                }else if(Integer.parseInt(Inches)>11 ){
                    eInches.setError(" Inches cannot be  greater than 11");
                }

                else if (Age.trim().isEmpty()) {
                    eAge.setError("Age required");

                } else if(Integer.parseInt(Age)==0 || Integer.parseInt(Age)>110){
                    eAge.setError(" Age cannot be 0 or  greater than 110");
                }
                    else if(Rate.trim().isEmpty()) {
                    activityrate.setError("Activity required");
                }

                else if(Integer.parseInt(Rate)==0 || Integer.parseInt(Rate)>5 ){
                    activityrate.setError("Invalid Activity! Please select from options below");
                }

                else if (dbHelper.isUserAlreadyExist(email.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                }

                else {
                    User user = new User();
                    user.setId(String.valueOf(System.currentTimeMillis()));
                    user.setEmail(email.getText().toString());
                    //user.setName(name.getText().toString());
                    //user.setMobilenumber(mobilenumber.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setWeight(eWeight.getText().toString());
                    user.setHeight(eHeight.getText().toString());
                    user.setInches(eInches.getText().toString());
                    user.setAge(eAge.getText().toString());
                    if (rmale.isChecked()) {
                        user.setGender("male");
                    } else {
                        user.setGender("female");
                    }
                    user.setActivity(activityrate.getText().toString());

                        dbHelper.registerUser(user);
                        Toast.makeText(getApplicationContext(), "User added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("user", (Serializable) user);
                        startActivity(intent);
                        finish();



                }
            }
        });
    }
}
