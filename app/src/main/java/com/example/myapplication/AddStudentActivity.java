package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.model.model.Student;
import com.example.myapplication.Data.model.repository.StudentRepository;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddStudentActivity extends AppCompatActivity {

    private EditText etName, etAddress, etMobile;
    private Button btnDob, btnSave;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private StudentRepository studentRepository;
    private Student editStudent;
    private Calendar dobCalendar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etMobile = findViewById(R.id.etMobile);
        btnDob = findViewById(R.id.btnDob);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnSave = findViewById(R.id.btnSave);

        studentRepository = new StudentRepository(this);
        dobCalendar = Calendar.getInstance();

        Intent intent = getIntent();
        int studentId = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String mobile = intent.getStringExtra("mobile");
        String dob = intent.getStringExtra("dob");
        String gender = intent.getStringExtra("gender");

        if (studentId != -1) {
            setTitle("Edit Student");
            etName.setText(name);
            etAddress.setText(address);
            etMobile.setText(mobile);
            btnDob.setText(dob);
            if ("Male".equals(gender)) {
                rbMale.setChecked(true);
            } else if ("Female".equals(gender)) {
                rbFemale.setChecked(true);
            }

            editStudent = new Student(studentId, name, address, mobile, dob, gender);
        } else {
            setTitle("Add Student");
        }

        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent();
            }
        });
    }


    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dobCalendar.set(Calendar.YEAR, year);
                        dobCalendar.set(Calendar.MONTH, month);
                        dobCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDobButton();
                    }
                },
                dobCalendar.get(Calendar.YEAR),
                dobCalendar.get(Calendar.MONTH),
                dobCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void updateDobButton() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        btnDob.setText(sdf.format(dobCalendar.getTime()));
    }

    private void saveStudent() {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String dob = btnDob.getText().toString().trim();
        String gender = rbMale.isChecked() ? "Male" : rbFemale.isChecked() ? "Female" : "";

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(mobile)
                || TextUtils.isEmpty(dob) || TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mobile.length() != 10 || !TextUtils.isDigitsOnly(mobile)) {
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(
                editStudent != null ? editStudent.getId() : 0,
                name, address, mobile, dob, gender);

        if (editStudent != null) {
            studentRepository.updateStudent(student);
            Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show();
        } else {
            studentRepository.addStudent(student);
            Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
        }

        finish(); // Close activity after saving
    }
}
