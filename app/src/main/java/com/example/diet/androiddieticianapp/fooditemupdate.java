package com.example.diet.androiddieticianapp;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.example.diet.androiddieticianapp.AddFoodActivity.addFoodActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.diet.androiddieticianapp.AIFood.DetectorActivity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class fooditemupdate extends AppCompatActivity {

    DbHelper dbHelper;
    Food food;
    Button addFood, selectImageGallery, btnUpdate, btnclear, btndelete;
    EditText calorie, name;
    private static final int GALLERY_ACTION_PICK_REQUEST_CODE = 1001;
    private static final int RequestPermissionCode = 9687;
    ImageView ivFood;
    Spinner spinner;
    private String imagePath = "";
    private String path;
    private String filePath;
    private String absolutePath = "";
    private String realPath = "";
    public static AddFoodActivity addFoodActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooditemupdate);
        food = (Food) getIntent().getExtras().getSerializable("food");
        selectImageGallery = (Button) findViewById(R.id.btn_select_image1);
        addFood = (Button) findViewById(R.id.btn_add_food);
        calorie = (EditText) findViewById(R.id.edt_calorie);
        name = (EditText) findViewById(R.id.edt_name);
        ivFood = (ImageView) findViewById(R.id.iv_food);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnclear = (Button) findViewById(R.id.btn_clear);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btndelete = (Button) findViewById(R.id.btn_delete);
        name.setText(food.getName());
        calorie.setText(food.getCalorie());
        dbHelper = new DbHelper(this);

        String[] spinnerArray = {"meat", "vegetable", "fruit"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        realPath = food.getImage();


        selectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionStorage()) {
                    openGallery();
                } else {
                    requestPermission();
                }
            }
        });


        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                name.setError(null);
                calorie.setText("");
                calorie.setError(null);

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Food name required");
                } else if (calorie.getText().toString().isEmpty()) {
                    calorie.setError("Calorie required");

                } else {
                    Food updatefood = new Food();
                    updatefood.setName(name.getText().toString());
                    updatefood.setCalorie(calorie.getText().toString());
                    updatefood.setCategory(spinner.getSelectedItem().toString());
                    updatefood.setImage(realPath);
                    dbHelper.updateFood(updatefood, food.getId());
                    Toast.makeText(getApplicationContext(), "Food updated successfully", Toast.LENGTH_SHORT).show();
                    finish();

                }
                try {

                } catch (Exception ex) {

                }
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(fooditemupdate.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("Are you sure to delete this food?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper.deleteFood(food.getId());
                                Toast.makeText(getApplicationContext(), "Food deleted Successfully", Toast.LENGTH_SHORT).show();
                                fooditemupdate.super.onBackPressed();
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

        File imgFile = new File(food.getImage());
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivFood.setImageBitmap(bitmap);
        }
    }


    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_ACTION_PICK_REQUEST_CODE);
    }

    private boolean checkPermissionStorage() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, RequestPermissionCode);
    }

    public Bitmap getBitMap(String image) {
        BitmapFactory.Options options;

        try {
            options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile("file:" + image, options);
            return bitmap;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_ACTION_PICK_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri mImageUri = data.getData();
                        imagePath = mImageUri.getPath();
                        File imgFile = new File(imagePath);
                        absolutePath = imgFile.getAbsolutePath();
                        realPath = ImageFilePath.getPath(fooditemupdate.this, data.getData());
                        ivFood.setImageURI(mImageUri);
                        File file = new File(mImageUri.getPath());//create path from uri
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Image not suported use small size image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }

        }

    }
}
