package com.example.diet.androiddieticianapp;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.diet.androiddieticianapp.AIFood.DetectorActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class AddFoodActivity extends AppCompatActivity {
    DbHelper dbHelper;
    Button selectImageAI, addFood,selectImageGallery;
    EditText calorie, name;
    private static final int GALLERY_ACTION_PICK_REQUEST_CODE = 1001;
    private static final int RequestPermissionCode = 9687;
    private static final String calorieInfoFile = "calorie_info.txt";
    ImageView ivFood;
    Spinner spinner;
    private String imagePath = "";
    private String path;
    private String filePath;
    private String absolutePath = "";
    private String realPath = "";
    private static HashMap<String, Integer> calorieInfo = new HashMap<>();
    public static AddFoodActivity addFoodActivity;
    String foodName;
    String calorieFromFood;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food_activity);
        addFoodActivity = this;
        dbHelper = new DbHelper(this);
        try {
            calorieInfo = loadCalorieInfo(calorieInfoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectImageAI = (Button) findViewById(R.id.btn_select_image);
        selectImageGallery=(Button) findViewById(R.id.btn_select_image1);
        addFood = (Button) findViewById(R.id.btn_add_food);
        calorie = (EditText) findViewById(R.id.edt_calorie);
        name = (EditText) findViewById(R.id.edt_name);
        ivFood = (ImageView) findViewById(R.id.iv_food);
        spinner = (Spinner) findViewById(R.id.spinner);


        String[] spinnerArray = {"meat", "vegetable", "fruit"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        selectImageAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DetectorActivity.class));
            }
        });

        selectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionStorage()) {
                   openGallery();
                } else { requestPermission(); }
            }
        });

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Food name required");
                } else if (calorie.getText().toString().isEmpty()) {
                    calorie.setError("Calorie required");

                } else if (realPath.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
                } else {
                    Food food = new Food();
                    food.setId(String.valueOf(System.currentTimeMillis()));
                    food.setImage(realPath);
                    food.setName(name.getText().toString());
                    food.setCalorie(calorie.getText().toString());
                    food.setCategory(spinner.getSelectedItem().toString());
                    dbHelper.addFood(food);
                    Toast.makeText(getApplicationContext(), "Food added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                try {

                } catch (Exception ex) {

                }
            }
        });

    }

    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_ACTION_PICK_REQUEST_CODE);
    }

    public boolean checkPermissionStorage() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, RequestPermissionCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_ACTION_PICK_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri mImageUri = data.getData();
                        imagePath = mImageUri.getPath();
                        File imgFile = new File(imagePath);
                        absolutePath = imgFile.getAbsolutePath();
                        realPath = ImageFilePath.getPath(AddFoodActivity.this, data.getData());
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

    public static AddFoodActivity getInstance() {
        return addFoodActivity;
    }

    public void addFood(String food, Bitmap bp) throws IOException {
        int calories = getCalorie(food);
        calorie.setText(String.valueOf(calories));
        name.setText(food);
        ivFood.setImageBitmap(bp);

        //create a file to write bitmap data
        File f = new File(getCacheDir(), String.valueOf(System.currentTimeMillis()));
        f.createNewFile();

//Convert bitmap to byte array
        Bitmap bitmap = bp;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        realPath = f.getAbsolutePath();
    }

    public int getCalorie(String food) {
        return calorieInfo.get(food);
    }

    private HashMap loadCalorieInfo(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)));
        HashMap<String, Integer> calCounts = new HashMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            calCounts.put(line.split(": ")[0], Integer.parseInt(line.split(": ")[1]));
        }
        return calCounts;
    }
}
