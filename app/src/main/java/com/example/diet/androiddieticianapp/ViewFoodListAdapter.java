package com.example.diet.androiddieticianapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ViewFoodListAdapter extends BaseAdapter {
    Context theActivity;
    ArrayList<Food> fd = new ArrayList<Food>();

    public ViewFoodListAdapter(Context ctx, ArrayList<Food> list) {
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


        newView = inflater.inflate(R.layout.foodlistrow, null, true);
        TextView name = (TextView) newView.findViewById(R.id.tv_food);
        TextView calorie=(TextView)newView.findViewById(R.id.fcalorie);
        TextView category=(TextView)newView.findViewById(R.id.fcategory);
        ImageView ivImage = (ImageView) newView.findViewById(R.id.foodimage);
        LinearLayout linRow = (LinearLayout) newView.findViewById(R.id.lin_row1);

        linRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(theActivity,fooditemupdate.class);
                intent.putExtra("food", (Serializable) food);
                theActivity.startActivity(intent);
            }
        });

        name.setText("Food Name: "+food.getName());
        calorie.setText("Calorie Contained: "+food.getCalorie());
        category.setText("Food Category: "+food.getCategory());
        File imgFile = new File(food.getImage());
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivImage.setImageBitmap(bitmap);
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

}