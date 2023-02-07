package com.example.diet.androiddieticianapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import java.io.Serializable;

public class UserModify extends AppCompatActivity {
    TextView email;
    EditText name,mobilenumber,epassword,eWeight, eHeight, eInches, eAge, activityrate;
    Button bupdate, bclear, bdelete, bback;
    DbHelper dbHelper;
    String userEmail;
    User user;
    private RadioButton rmale, rfemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userupdate);
        user = (User) getIntent().getExtras().getSerializable("user");
        userEmail = user.getEmail();
        dbHelper = new DbHelper(this);
        email=(TextView) findViewById(R.id.textemail);
        //name=(EditText) findViewById(R.id.change_name);
        //mobilenumber=(EditText) findViewById(R.id.changeMobileNumber);
        epassword=(EditText) findViewById(R.id.editpassword);
        eWeight = (EditText) findViewById(R.id.editText);
        eHeight = (EditText) findViewById(R.id.editText2);
        eInches = (EditText) findViewById(R.id.editText3);
        eAge = (EditText) findViewById(R.id.editText4);
        activityrate = (EditText) findViewById(R.id.editText16);
        rmale = (RadioButton) findViewById(R.id.radioButton);
        rfemale = (RadioButton) findViewById(R.id.radioButton1);
        bupdate = (Button) findViewById(R.id.button3);
        bclear = (Button) findViewById(R.id.button2);
        bdelete = (Button) findViewById(R.id.button4);
        bback=(Button) findViewById(R.id.button5);

        email.setText("Email address: "+user.getEmail());
        //name.setText(user.getName());
        //mobilenumber.setText(user.getMobilenumber());
        epassword.setText(user.getPassword());
        eWeight.setText(user.getWeight());
        eHeight.setText(user.getHeight());
        eInches.setText(user.getInches());
        eAge.setText(user.getAge());
        activityrate.setText(user.getActivity());
        if(user.getGender().equals("male")){
            rmale.setChecked(true);
        }else{
            rfemale.setChecked(true);
        }

        bupdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //String Name=name.getText().toString().trim();
                //String Mobile=mobilenumber.getText().toString().trim();
                String Password=epassword.getText().toString().trim();
                String Weight = eWeight.getText().toString();
                String Height = eHeight.getText().toString();
                String Inches = eInches.getText().toString();
                String Age = eAge.getText().toString();
                String Activity = activityrate.getText().toString();


                User updateUser = new User();
                //updateUser.setName(Name);
                //updateUser.setMobilenumber(Mobile);
                updateUser.setPassword(Password);
                updateUser.setWeight(Weight);
                updateUser.setHeight(Height);
                updateUser.setInches(Inches);
                updateUser.setAge(Age);
                updateUser.setId(user.getId());
                updateUser.setEmail(user.getEmail());
                if (rmale.isChecked()) {
                    updateUser.setGender("male");
                } else {
                    updateUser.setGender("female");
                }

                AlertDialog alertDialog = new AlertDialog.Builder( UserModify.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("Are you sure to update this user ?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               /* if(Name.isEmpty()){
                                    name.setError("Name cannot be empty");
                                }
                                else if(Mobile.isEmpty()){
                                    mobilenumber.setError("Mobile number cannot be empty");
                                }*/
                                 if(Password.trim().isEmpty()){
                                    epassword.setError("Password can be changed but cannot be empty or 0");
                                }
                                else if(Weight.trim().isEmpty()){
                                    eWeight.setError("Weight cannot be empty");
                                }
                                else if(Float.parseFloat(Weight)>300 || Float.parseFloat(Weight)==0){
                                    eWeight.setError("Weight cannot be 0 or greater than 300 kg");
                                }
                                else if(Height.trim().isEmpty()){
                                    eHeight.setError("Feet cannot be empty");
                                }
                                else if(Integer.parseInt(Height)>8 || Integer.parseInt(Height)==0){
                                    eHeight.setError("Feet cannot be  0 or greater than 8");
                                }
                                else if(Inches.trim().isEmpty()){
                                    eInches.setError("Inches cannot be empty");
                                }
                                else if(Integer.parseInt(Inches)>11){
                                    eInches.setError(" Inches cannot be greater than 11");
                                }
                                else if(Age.trim().isEmpty()){
                                    eAge.setError("Age cannot be empty");
                                }
                                else if(Integer.parseInt(Age)>110 || Integer.parseInt(Age)==0){
                                    eAge.setError(" Age cannot be 0 or  greater than 110");
                                }
                                    else if(Activity.isEmpty()){
                                        activityrate.setError("Activity cannot be empty");

                                    }
                                    else if(Integer.parseInt(Activity)==0 || Integer.parseInt(Activity)>5  ){
                                        activityrate.setError("Invalid activity! Please select from options below");
                                    }
                                    else {
                                        updateUser.setActivity(Activity);
                                        dbHelper.updateUser(updateUser);
                                        Toast.makeText(getApplicationContext(), "User updated Successfully", Toast.LENGTH_SHORT).show();
                                        UserModify.super.onBackPressed();
                                        finish();
                                    }
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();



            }
        });
        bdelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder( UserModify.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("Are you sure to delete this user ?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper.deleteFromTable(user.getId());
                                Toast.makeText(getApplicationContext(), "User deleted Successfully", Toast.LENGTH_SHORT).show();
                                UserModify.super.onBackPressed();
                                finish();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        })
                        .show();

            }
        });
        bclear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                eWeight.setText("");
                eWeight.setError(null);
                eHeight.setText("");
                eHeight.setError(null);
                eInches.setText("");
                eInches.setError(null);
                eAge.setText("");
                eAge.setError(null);
                activityrate.setText("");
                activityrate.setError(null);
                rmale.setChecked(true);
                epassword.setError(null);
                epassword.setText("");
                rmale.setEnabled(true);
            }
        });




    }
}