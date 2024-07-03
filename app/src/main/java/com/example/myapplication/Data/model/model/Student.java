package com.example.myapplication.Data.model.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private int id;
    private String name;
    private String address;
    private String mobileNumber;
    private String dob;
    private String gender;

    public Student(int id, String name, String address, String mobileNumber, String dob, String gender) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.dob = dob;
        this.gender = gender;
    }

    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        mobileNumber = in.readString();
        dob = in.readString();
        gender = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(mobileNumber);
        dest.writeString(dob);
        dest.writeString(gender);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
