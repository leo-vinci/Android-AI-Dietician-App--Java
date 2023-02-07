package com.example.diet.androiddieticianapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {

    Context theActivity;
    ArrayList<Food> fd = new ArrayList<Food>();

    public FoodListAdapter(Context ctx, ArrayList<Food> list) {
        theActivity = ctx;
        fd = list;
    }

    @Override
    public int getCount() {

        return fd.size();
    }

    @Override
    public Object getItem(int i) {

        return null;
    }

    @Override
    public long getItemId(int i) {

        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        View newView = null;

        Food food = fd.get(pos);

        LayoutInflater inflater = LayoutInflater.from(theActivity);


        newView = inflater.inflate(R.layout.food_row_item, null, true);
        TextView name=(TextView) newView.findViewById(R.id.name1);
        TextView calorie = (TextView) newView.findViewById(R.id.t1);
        AppCompatImageView ivImage = (AppCompatImageView) newView.findViewById(R.id.imageView);
        EditText edtCalorie= (EditText) newView.findViewById(R.id.e1);
        calorie.setText("1 of " + food.getCalorie() + " cal");
        food.setEditText(edtCalorie);
        name.setText(food.getName());
        File imgFile = new File(food.getImage());
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivImage.setImageBitmap(bitmap);
            try {
                BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }

        }

        return newView;
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

    public ArrayList<Food> newList(){
        return fd;
    }

}
