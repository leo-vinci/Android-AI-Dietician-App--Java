package com.example.diet.androiddieticianapp;

import android.widget.EditText;

import java.io.Serializable;

public class Food implements Serializable {

    private String id;
    private String name;
    private String calorie;
    private String image;
    private String category;

    private EditText editText;

    public String getName(){ return name; }

    public void setName(String name) { this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}
