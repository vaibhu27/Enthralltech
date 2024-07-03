package com.example.myapplication.Data.model.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Data.model.db.DatabaseHelperOne;
import com.example.myapplication.Data.model.model.User;


public class UserRepository {

    private DatabaseHelperOne dbHelper;

    public UserRepository(Context context) {
        dbHelper = new DatabaseHelperOne(context);
    }

    public boolean login(String mobile, String mpin) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelperOne.TABLE_USERS, null,
                DatabaseHelperOne.COLUMN_MOBILE + "=? AND " + DatabaseHelperOne.COLUMN_MPIN + "=?",
                new String[]{mobile, mpin}, null, null, null);

        boolean success = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return success;
    }

    public long addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelperOne.COLUMN_MOBILE, user.getMobile());
        values.put(DatabaseHelperOne.COLUMN_MPIN, user.getMpin()); // Assuming you have MPIN in your User model

        long id = db.insert(DatabaseHelperOne.TABLE_USERS, null, values); // Corrected table name
        db.close();
        return id;
    }
    public User getUser(String mobile) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelperOne.TABLE_USERS,
                new String[]{DatabaseHelperOne.COLUMN_ID, DatabaseHelperOne.COLUMN_MOBILE, DatabaseHelperOne.COLUMN_MPIN},
                DatabaseHelperOne.COLUMN_MOBILE + "=?",
                new String[]{mobile}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            User user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperOne.COLUMN_MOBILE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelperOne.COLUMN_MPIN)));
            cursor.close();
            db.close();
            return user;
        } else {
            return null;
        }
    }


}
