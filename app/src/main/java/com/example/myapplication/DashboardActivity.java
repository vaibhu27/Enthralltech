// com.example.loginapp.ui.dashboard.DashboardActivity.java

package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.StudentAdapter;
import com.example.myapplication.Data.model.model.Student;
import com.example.myapplication.R;
import  com.example.myapplication.Data.model.repository.StudentRepository;


import java.util.List;

public class DashboardActivity extends AppCompatActivity implements StudentAdapter.StudentClickListener {

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private StudentRepository studentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentAdapter(this);
        recyclerView.setAdapter(studentAdapter);

        studentRepository = new StudentRepository(this);

        loadStudents();

        ImageView addStudentButton = findViewById(R.id.btnAddStudent);
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadStudents() {
        List<Student> students = studentRepository.getAllStudents();
        studentAdapter.setStudents(students);
    }

    @Override
    public void onEditStudent(Student student) {
        Intent intent = new Intent(DashboardActivity.this, AddStudentActivity.class);
        intent.putExtra("id", student.getId());
        intent.putExtra("name", student.getName());
        intent.putExtra("address", student.getAddress());
        intent.putExtra("mobile", student.getMobileNumber());
        intent.putExtra("dob", student.getDob());
        intent.putExtra("gender", student.getGender());
        startActivity(intent);
    }

    @Override
    public void onDeleteStudent(final Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete " + student.getName() + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                studentRepository.deleteStudent(student.getId());
                Toast.makeText(DashboardActivity.this, student.getName() + " deleted", Toast.LENGTH_SHORT).show();
                loadStudents(); // Refresh the list after deletion
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onStart() {
        loadStudents();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        super.onStart();
    }
}

