package com.example.diet.androiddieticianapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    String userTable = "CREATE TABLE " + "user" + "( "
            + "id" + " TEXT primary key , "
            + "email" + " TEXT unique, "
            + "password" + " TEXT, "
            + "weight" + " TEXT, "
            + "height" + " TEXT, "
            + "inches" + " TEXT, "
            + "age" + " TEXT,"
            + "gender" + " TEXT, "
            + "activity" + " TEXT" + ")";

    String food = "CREATE TABLE " + "food" + "( "
            + "id" + " TEXT, "
            + "name" + " TEXT, "
            + "calorie" + " TEXT, "
            + "category" + " TEXT, "
            + "image" + " TEXT" + ")";

    String feedback = "CREATE TABLE " + "feedback" + "( "
            + "id" + " TEXT, "
            + "email" + " TEXT, "
            + "message" + " TEXT" + ")";

    String response = "CREATE TABLE " + "response" + "( "
            + "id" + " TEXT, "
            + "email" + " TEXT, "
            + "userEmail" + " TEXT, "
            + "responsemessage" + " TEXT" + ")";

    public DbHelper(Context context) {
        super(context, "user.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(userTable);
        sqLiteDatabase.execSQL(food);
        sqLiteDatabase.execSQL(feedback);
        sqLiteDatabase.execSQL(response);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void registerUser(User user) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        //values.put("name",user.getName());
        //values.put("mobilenumber",user.getMobilenumber());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("weight", user.getWeight());
        values.put("height", user.getHeight());
        values.put("inches", user.getInches());
        values.put("age", user.getAge());
        values.put("gender", user.getGender());
        values.put("activity", user.getActivity());
        long result = sqLiteDatabase.insertWithOnConflict("user", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }
    public User getUser(String em, String pw) {
        User user = null;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String getAllData = "SELECT * FROM user where email='" + em + "' and password='" + pw + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(getAllData, null);
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            //String name=cursor.getString(cursor.getColumnIndex("name"));
            //String mobile=cursor.getString(cursor.getColumnIndex("mobilenumber"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String weight = cursor.getString(cursor.getColumnIndex("weight"));
            String height = cursor.getString(cursor.getColumnIndex("height"));
            String inches = cursor.getString(cursor.getColumnIndex("inches"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String activity = cursor.getString(cursor.getColumnIndex("activity"));

            User user1 = new User();
            user1.setId(id);
            user1.setEmail(email);
            //user1.setName(name);
            //user1.setMobilenumber(mobile);
            user1.setPassword(password);
            user1.setWeight(weight);
            user1.setHeight(height);
            user1.setInches(inches);
            user1.setAge(age);
            user1.setGender(gender);
            user1.setActivity(activity);

            cursor.close();
            return user1;
        }
        return null;
    }

    public ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String getAllData = "SELECT * FROM user";

        Cursor cursor = sqLiteDatabase.rawQuery(getAllData, null);
        if (cursor.moveToFirst()) {
            userList = new ArrayList<>();
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));

                String email = cursor.getString(cursor.getColumnIndex("email"));
                //String name=cursor.getString(cursor.getColumnIndex("name"));
                //String mobile=cursor.getString(cursor.getColumnIndex("mobilenumber"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String weight = cursor.getString(cursor.getColumnIndex("weight"));
                String height = cursor.getString(cursor.getColumnIndex("height"));
                String inches = cursor.getString(cursor.getColumnIndex("inches"));
                String age = cursor.getString(cursor.getColumnIndex("age"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));
                String activity = cursor.getString(cursor.getColumnIndex("activity"));

                User user1 = new User();
                user1.setId(id);
                user1.setEmail(email);
                //user1.setName(name);
                //user1.setMobilenumber(mobile);
                user1.setPassword(password);
                user1.setWeight(weight);
                user1.setHeight(height);
                user1.setInches(inches);
                user1.setAge(age);
                user1.setGender(gender);
                user1.setActivity(activity);

                userList.add(user1);

            } while (cursor.moveToNext());
            cursor.close();

            return userList;
        }
        return userList;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("name",user.getName());
        //values.put("mobilenumber",user.getMobilenumber());
        values.put("password",user.getPassword());
        values.put("weight", user.getWeight());
        values.put("height", user.getHeight());
        values.put("inches", user.getInches());
        values.put("age", user.getAge());
        values.put("gender", user.getGender());
        values.put("activity",user.getActivity());

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user where email='" + user.getEmail() + "'", null);
        if (cursor.getCount() > 0) {
            long result = sqLiteDatabase.update("user", values, "email=?", new String[]{user.getEmail()});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public Boolean updatePassword(String email, String password){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password",password);
        values.put("email",email);
        long result=sqLiteDatabase.update("user",values,"email =?",new String[]{email} );
        if(result==-1) return false;
        else{
            return true;
        }

    }


    public void addFood(Food food) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", food.getId());
        values.put("category", food.getCategory());
        values.put("image", food.getImage());
        values.put("calorie", food.getCalorie());
        values.put("name", food.getName());
        long result = sqLiteDatabase.insertWithOnConflict("food", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }
    public boolean updateFood(Food food , String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("category", food.getCategory());
        values.put("image", food.getImage());
        values.put("calorie", food.getCalorie());
        values.put("name", food.getName());

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM food where id='" + id + "'", null);
        if (cursor.getCount() > 0) {
            long result = sqLiteDatabase.update("food", values, "id=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    public void deleteFood(String uuid) {
        String deleteQuery = "DELETE FROM " + "food" + " WHERE " + "id" + "='" + uuid +
                "'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(deleteQuery);
    }

    public ArrayList<Food> getFoods(String category) {
        ArrayList<Food> foods = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String getAllData = "SELECT * FROM food where category='" + category + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(getAllData, null);
        if (cursor.moveToFirst()) {
            foods = new ArrayList<>();
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String foodCategory = cursor.getString(cursor.getColumnIndex("category"));
                String calorie = cursor.getString(cursor.getColumnIndex("calorie"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String image = cursor.getString(cursor.getColumnIndex("image"));

                Food food = new Food();
                food.setId(id);
                food.setImage(image);
                food.setCalorie(calorie);
                food.setName(name);
                food.setCategory(category);

                foods.add(food);

            } while (cursor.moveToNext());
            cursor.close();

            return foods;
        }
        return foods;
    }

    public ArrayList<Food> getAllFoods() {
        ArrayList<Food> foods = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String getAllData = "SELECT * FROM food";

        Cursor cursor = sqLiteDatabase.rawQuery(getAllData, null);
        if (cursor.moveToFirst()) {
            foods = new ArrayList<>();
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String foodCategory = cursor.getString(cursor.getColumnIndex("category"));
                String calorie = cursor.getString(cursor.getColumnIndex("calorie"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String image = cursor.getString(cursor.getColumnIndex("image"));

                Food food = new Food();
                food.setId(id);
                food.setImage(image);
                food.setCalorie(calorie);
                food.setName(name);
                food.setCategory(foodCategory);

                foods.add(food);

            } while (cursor.moveToNext());
            cursor.close();

            return foods;
        }
        return foods;
    }

    public void addFeedBack(FeedBack feedBack) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", feedBack.getId());
        values.put("email", feedBack.getEmail());
        values.put("message", feedBack.getMessage());
        long result = sqLiteDatabase.insertWithOnConflict("feedback", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<FeedBack> getFeedbacks() {
        ArrayList<FeedBack> feedBacks = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String getAllData = "SELECT * FROM feedback";

        Cursor cursor = sqLiteDatabase.rawQuery(getAllData, null);
        if (cursor.moveToFirst()) {
            feedBacks = new ArrayList<>();
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String message = cursor.getString(cursor.getColumnIndex("message"));

                FeedBack feedBack = new FeedBack();
                feedBack.setId(id);
                feedBack.setEmail(email);
                feedBack.setMessage(message);

                feedBacks.add(feedBack);

            } while (cursor.moveToNext());
            cursor.close();

            return feedBacks;
        }
        return feedBacks;
    }

    public void addResponse(Response response) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", response.getId());
        values.put("email", response.getEmail());
        values.put("userEmail", response.getUserEmail());
        values.put("responsemessage", response.getResponsemessage());
        long result = sqLiteDatabase.insertWithOnConflict("response", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<Response> getResponse(String em) {
        ArrayList<Response> responses = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String getAllData = "SELECT * FROM response WHERE userEmail='" + em + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(getAllData, null);
        if (cursor.moveToFirst()) {
            responses = new ArrayList<>();
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String responseMessage = cursor.getString(cursor.getColumnIndex("responsemessage"));

                Response response = new Response();
                response.setId(id);
                response.setEmail(email);
                response.setResponsemessage(responseMessage);

                responses.add(response);

            } while (cursor.moveToNext());
            cursor.close();

            return responses;
        }
        return responses;
    }


    public void deleteFromTable(String uuid) {
        String deleteQuery = "DELETE FROM " + "user" + " WHERE " + "id" + "='" + uuid +
                "'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(deleteQuery);
    }

    public boolean isUserAlreadyExist(String em) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String getAllData = "SELECT * FROM user where email='" + em + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(getAllData, null);
        return cursor.moveToFirst();
    }

}
