package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.model.model.Student;
import com.example.myapplication.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> students;
    private StudentClickListener clickListener;

    public StudentAdapter(StudentClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return students != null ? students.size() : 0;
    }

    class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName, tvAddress, tvMobile, tvDob, tvGender;
        private ImageView btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvDob = itemView.findViewById(R.id.tvDob);
            tvGender = itemView.findViewById(R.id.tvGender);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }

        public void bind(Student student) {
            tvName.setText(student.getName());
            tvAddress.setText(student.getAddress());
            tvMobile.setText(student.getMobileNumber());
            tvDob.setText(student.getDob());
            tvGender.setText(student.getGender());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Student student = students.get(position);

                // Check view ID and call appropriate method on clickListener
                if (v.getId() == R.id.btnEdit) {
                    clickListener.onEditStudent(student);
                } else if (v.getId() == R.id.btnDelete) {
                    clickListener.onDeleteStudent(student);
                }
            }
        }

    }

    public interface StudentClickListener {
        void onEditStudent(Student student);
        void onDeleteStudent(Student student);
    }
}
