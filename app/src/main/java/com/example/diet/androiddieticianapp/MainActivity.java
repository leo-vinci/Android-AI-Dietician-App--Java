package com.example.diet.androiddieticianapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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

public class MainActivity extends AppCompatActivity {
    EditText eWeight, eHeight, eInches, eAge, activityrate, epassword;
    TextView bodytype,email;
    TextView tBMILable, tBMIResult, tBMRLable, tBMRResult;
    TextView tcalories, tcaloriesresult;
    Button bcalculate, bclear, bdietplan,bupdate;
    float weight, value, age, BMI, resultf, resultfb, h, hm1,hm2,hm3,hm4, i, calorieintake;
    int rate;
    double result, bmrMen, bmrWomen;
    RadioButton rmale, rfemale;
    User user;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (User) getIntent().getExtras().getSerializable("user");
        dbHelper = new DbHelper(this);
        tBMILable = (TextView) findViewById(R.id.textView9);
        tBMIResult = (TextView) findViewById(R.id.textView10);
        tBMRLable = (TextView) findViewById(R.id.textView11);
        tBMRResult = (TextView) findViewById(R.id.textView12);
        tcalories = (TextView) findViewById(R.id.textView15);
        tcaloriesresult = (TextView) findViewById(R.id.textView14);
        bodytype = (TextView) findViewById(R.id.textView13);
        email=(TextView) findViewById(R.id.textemail);
        epassword=(EditText) findViewById(R.id.editpassword);
        eWeight = (EditText) findViewById(R.id.editText);
        eHeight = (EditText) findViewById(R.id.editText2);
        eInches = (EditText) findViewById(R.id.editText3);
        eAge = (EditText) findViewById(R.id.editText4);
        activityrate = (EditText) findViewById(R.id.editText16);
        bcalculate = (Button) findViewById(R.id.button);
        bclear = (Button) findViewById(R.id.button2);
        bdietplan = (Button) findViewById(R.id.button3);
        bupdate=(Button) findViewById(R.id.buttonupdate);
        rmale = (RadioButton) findViewById(R.id.radioButton);
        rfemale = (RadioButton) findViewById(R.id.radioButton1);
        bdietplan.setVisibility(INVISIBLE);
        epassword.setText(user.getPassword());
        email.setText("Email address: "+user.getEmail());
        saveEmail(user.getEmail());
        rfemale.setChecked(true);
        if (user.getGender().equals("male")) {
            rmale.setChecked(true);
        }
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
                tBMIResult.setVisibility(INVISIBLE);
                tBMILable.setVisibility(INVISIBLE);
                bodytype.setVisibility(INVISIBLE);
                tBMRLable.setVisibility(INVISIBLE);
                tBMIResult.setVisibility(INVISIBLE);
                tBMRResult.setVisibility(INVISIBLE);
                rmale.setChecked(false);
                rmale.setEnabled(true);
                rfemale.setChecked(false);
                rfemale.setEnabled(true);
                tcalories.setVisibility(INVISIBLE);
                tcaloriesresult.setVisibility(INVISIBLE);
                bdietplan.setVisibility(INVISIBLE);
                rfemale.setChecked(true);

            }
        });
        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password=epassword.getText().toString();
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

                AlertDialog alertDialog = new AlertDialog.Builder( MainActivity.this)

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
                                 if(Weight.trim().isEmpty()){
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

        bcalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (eWeight.length() == 0 || eHeight.length() == 0 || eAge.length() == 0 || activityrate.length() == 0) {
                    if (eWeight.length() == 0) {
                        eWeight.setError("Enter Weight");
                    } else if (eHeight.length() == 0) {
                        eHeight.setError("Enter the height");
                    } else if (eAge.length() == 0) {
                        eAge.setError("Enter the age");
                    } else if (activityrate.length() == 0) {
                        activityrate.setError("Enter the activity");
                    }

                } else {
                    if (eInches.length() == 0) {

                        eInches.setText((String.valueOf(0)));
                        weight = Float.parseFloat(eWeight.getText().toString());
                        h = Float.parseFloat(eHeight.getText().toString());
                        i = Float.parseFloat(eInches.getText().toString());
                        age = Float.parseFloat(eAge.getText().toString());
                        rate = Integer.parseInt(activityrate.getText().toString());
                        String srate = activityrate.getText().toString();
                        optionsChecked();

                        calculateBMI();
                        calculateBMR();
                        bdietplan.setVisibility(VISIBLE);
                    } else {
                        weight = Float.parseFloat(eWeight.getText().toString());
                        h = Float.parseFloat(eHeight.getText().toString());
                        i = Float.parseFloat(eInches.getText().toString());
                        age = Float.parseFloat(eAge.getText().toString());
                        rate = Integer.parseInt(activityrate.getText().toString());
                        String srate = activityrate.getText().toString();
                        optionsChecked();

                        calculateBMI();
                        calculateBMR();
                        bdietplan.setVisibility(VISIBLE);
                    }

                }

            }
        });

        bdietplan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("bmi is", "value" + resultfb);
                optionsChecked();

                if (resultfb <= 18.5) {
                    Intent t = new Intent(MainActivity.this, DietPlan.class);
                    String s = Float.toString(calorieintake);
                    Log.d("bmi is", "value" + calorieintake);
                    t.putExtra("calories", s);
                    startActivityForResult(t, 0);
                } else if (resultfb >= 18.5 && resultfb <= 24.9) {
                    Intent t1 = new Intent(MainActivity.this, NormalWeight.class);
                    String s = Float.toString(calorieintake);
                    Log.d("bmi is", "value" + calorieintake);
                    t1.putExtra("calories", s);
                    startActivityForResult(t1, 0);
                } else if (resultfb >= 25.0 && resultfb <= 29.9) {

                    Intent t1 = new Intent(MainActivity.this, OverWeight.class);
                    String s = Float.toString(calorieintake);
                    Log.d("bmi is", "value" + calorieintake);
                    t1.putExtra("calories", s);
                    startActivityForResult(t1, 0);

                } else if ((resultfb >= 30.0)) {
                    Intent t1 = new Intent(MainActivity.this, Obese.class);
                    String s = Float.toString(calorieintake);
                    t1.putExtra("calories", s);
                    Log.d("bmi is", "value" + calorieintake);
                    startActivityForResult(t1, 0);
                }
            }
        });

        if (user != null) {
            eWeight.setText(user.getWeight());
            eHeight.setText(user.getHeight());
            eInches.setText(user.getInches());
            eAge.setText(user.getAge());
            activityrate.setText(user.getActivity());
        }
    }

    public void calculateBMI() {

        hm1 = (float) (h*0.3048);//hm1=heighinft to meter
        hm2 = (float) (i*0.0254);//hm2 =heightininch to meter
        hm3=hm1+hm2;
        BMI=weight/(hm3*hm3);
        hm4= (float) (hm3*100);//meter in cm

        result = (float) (Math.round(BMI * 100.0) / 100.0);
        resultfb = (float) result;
        tBMIResult.setVisibility(VISIBLE);
        tBMIResult.setTextSize(18);
        tBMIResult.setTypeface(tBMIResult.getTypeface(), Typeface.BOLD);
        tBMIResult.setText(Float.toString(resultfb));
        tBMILable.setVisibility(VISIBLE);
        tBMILable.setTextSize(18);
        tBMILable.setTypeface(tBMILable.getTypeface(), Typeface.BOLD);
        displayBodyType();

    }

    public void displayBodyType() {
        optionsChecked();
        if (resultfb <= 18.5) {
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("UnderWeight");
        } else if (resultfb >= 18.5 && resultfb <= 24.9) {
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("NormalWeight");

        } else if (resultfb >= 25.0 && resultfb <= 29.9) {
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("OverWeight");

        } else if (resultfb >= 30.0) {
            bodytype.setVisibility(VISIBLE);
            bodytype.setTextSize(18);
            bodytype.setTypeface(bodytype.getTypeface(), Typeface.BOLD);
            bodytype.setText("Obese");
        } else {

        }
    }

    public void calculateBMR() {
        optionsChecked();
        if (rmale.isChecked()) {
            bmrMen=(10* weight)+(6.25 *hm4)-(5*age)+5;
            Log.d("male", "value" + bmrMen);
            result = (Math.round(bmrMen * 100.0) / 100.0);
            resultf = (float) result;
            Log.d("male", "value" + bmrMen);
            tBMRLable.setVisibility(VISIBLE);
            tBMRLable.setTextSize(18);
            tBMRLable.setTypeface(tBMRLable.getTypeface(), Typeface.BOLD);
            tBMRResult.setVisibility(VISIBLE);
            tBMRResult.setTextSize(18);
            tBMRResult.setTypeface(tBMRResult.getTypeface(), Typeface.BOLD);

            bmrMen = Math.round(bmrMen);
            tBMRResult.setText(Double.toString(bmrMen));
            tcalories.setVisibility(VISIBLE);
            tcalories.setTextSize(18);
            tcalories.setTypeface(tcalories.getTypeface(), Typeface.BOLD);
            tcaloriesresult.setVisibility(VISIBLE);
            tcaloriesresult.setTextSize(18);
            tcaloriesresult.setTypeface(tcaloriesresult.getTypeface(), Typeface.BOLD);
            rate = Integer.parseInt(activityrate.getText().toString());
            if (rate == 1) {
                calorieintake = resultf * (float) 1.2;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            } else if (rate == 2) {
                calorieintake = resultf * (float) 1.375;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            } else if (rate == 3) {
                calorieintake = resultf * (float) 1.55;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            } else if (rate == 4) {
                calorieintake = resultf * (float) 1.725;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            } else if (rate == 5) {
                calorieintake = resultf * (float) 1.9;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            } else {
                activityrate.setError("Invalid Activity. Please select from options below");
            }
        } else if (rfemale.isChecked()) {
            bmrWomen=(10*weight)+(6.25*hm4)-(5*age)-161;

            result = (Math.round(bmrWomen * 100.0) / 100.0);
            resultf = (float) result;
            Log.d("female", "value" + bmrWomen);
            tBMRLable.setVisibility(VISIBLE);
            tBMRLable.setTextSize(18);
            tBMRLable.setTypeface(tBMRLable.getTypeface(), Typeface.BOLD);

            tBMRResult.setVisibility(VISIBLE);
            tBMRResult.setTextSize(18);
            tBMRResult.setTypeface(tBMRResult.getTypeface(), Typeface.BOLD);

            tcalories.setVisibility(VISIBLE);
            tcalories.setTextSize(18);
            tcalories.setTypeface(tcalories.getTypeface(), Typeface.BOLD);

            tcaloriesresult.setVisibility(VISIBLE);
            tcaloriesresult.setTextSize(18);
            tcaloriesresult.setTypeface(tcalories.getTypeface(), Typeface.BOLD);

            bmrWomen = Math.round(bmrWomen);
            tBMRResult.setText(Double.toString(bmrWomen));
            rate = Integer.parseInt(activityrate.getText().toString());
            if (rate == 1) {
                calorieintake = resultf * (float) 1.2;
                calorieintake = Math.round(calorieintake);
                Log.d("female", "value" + calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            } else if (rate == 2) {
                calorieintake = resultf * (float) 1.375;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            } else if (rate == 3) {
                calorieintake = resultf * (float) 1.55;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));

            } else if (rate == 4) {
                calorieintake = resultf * (float) 1.725;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            } else if (rate == 5) {
                calorieintake = resultf * (float) 1.9;
                calorieintake = Math.round(calorieintake);
                result = (Math.round(calorieintake * 100.0));
                resultf = (float) result;
                tcaloriesresult.setText(Double.toString(calorieintake));
            } else { activityrate.setError("Invalid Activity. Please select from options below");
            }

        }

    }

    public void optionsChecked() {
        Log.d("inside", "male" + rmale.isChecked());
        Log.d("inside", "female" + rfemale.isChecked());
        if (rmale.isChecked()) {
            Log.d("inside", "female");
            rfemale.setChecked(false);
        } else {
            Log.d("inside", "male");
            rmale.setChecked(false);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.log_out: {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            }
            case R.id.response:{
                startActivity(new Intent(MainActivity.this,ResponseActivity.class));
                return true;
            }
            case R.id.feedback: {
                addFeedBack();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void addFeedBack() {
        // custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Title");

// set the custom dialog components - text, image and button
        EditText edtFeedback = (EditText) dialog.findViewById(R.id.editText);
        Button btnSend = (Button) dialog.findViewById(R.id.btn_feedback);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtFeedback.getText().toString().trim().isEmpty()) {
                    edtFeedback.setError("Cannot be empty");
                }
                else{
                    FeedBack feedBack = new FeedBack();
                    feedBack.setId(String.valueOf(System.currentTimeMillis()));
                    feedBack.setMessage(edtFeedback.getText().toString());
                    feedBack.setEmail(user.getEmail());
                    dbHelper.addFeedBack(feedBack);
                    Toast.makeText(getApplicationContext(), "Feedback sent successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void saveEmail(String email) {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.apply();
    }


}