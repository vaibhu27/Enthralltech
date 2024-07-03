package com.example.myapplication.Data.model.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.myapplication.Data.model.db.DatabaseHelper;
import com.example.myapplication.Data.model.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private DatabaseHelper dbHelper;

    public StudentRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_STUDENTS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS));
                String mobileNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MOBILE));
                String dob = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DOB));
                String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENDER));

                Student student = new Student(id, name, address, mobileNumber, dob, gender);
                students.add(student);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return students;
    }

    public long addStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, student.getName());
        values.put(DatabaseHelper.COLUMN_ADDRESS, student.getAddress());
        values.put(DatabaseHelper.COLUMN_MOBILE, student.getMobileNumber());
        values.put(DatabaseHelper.COLUMN_DOB, student.getDob());
        values.put(DatabaseHelper.COLUMN_GENDER, student.getGender());

        long id = db.insert(DatabaseHelper.TABLE_STUDENTS, null, values);
        db.close();
        return id;
    }

    public void updateStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, student.getName());
        values.put(DatabaseHelper.COLUMN_ADDRESS, student.getAddress());
        values.put(DatabaseHelper.COLUMN_MOBILE, student.getMobileNumber());
        values.put(DatabaseHelper.COLUMN_DOB, student.getDob());
        values.put(DatabaseHelper.COLUMN_GENDER, student.getGender());

        db.update(DatabaseHelper.TABLE_STUDENTS, values, DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(student.getId())});

        db.close();
    }

    public void deleteStudent(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_STUDENTS, DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
