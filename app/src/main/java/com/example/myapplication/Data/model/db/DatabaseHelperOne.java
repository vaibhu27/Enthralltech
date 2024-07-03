package com.example.myapplication.Data.model.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperOne extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_MPIN = "mpin";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MOBILE + " TEXT, " +
                    COLUMN_MPIN + " TEXT);";

    public DatabaseHelperOne(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

        // Insert demo data
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOBILE, "1234567890");
        values.put(COLUMN_MPIN, "1234");
        db.insert(TABLE_USERS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
