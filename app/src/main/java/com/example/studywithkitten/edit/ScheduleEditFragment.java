package com.example.studywithkitten.edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studywithkitten.MainActivity;
import com.example.studywithkitten.R;
import com.example.studywithkitten.components.Course;
import com.example.studywithkitten.fragments.ScheduleFragment;
import com.example.studywithkitten.fragments.TodoFragment;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ScheduleEditFragment extends Fragment {

    EditText title;
    EditText number;
    EditText date;
    EditText time;
    EditText location;

    Button btnSave;

    List<String> courses = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_schedule_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.title);
        number = view.findViewById(R.id.number);
        date = view.findViewById(R.id.date);
        number = view.findViewById(R.id.number);
        time = view.findViewById(R.id.time);
        location = view.findViewById(R.id.location);
        btnSave = view.findViewById(R.id.btnSave);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Course");
        Course course = new Course();
        course.setTitle(title.getText().toString());
        course.setNumber(number.getText().toString());
        course.setDate(date.getText().toString());
        course.setTime(time.getText().toString());
        course.setLocation(location.getText().toString());

        if (course != null) {
            courses.add(course.toString());
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. save info to data.txt
                saveItems();
                Toast.makeText(getActivity(), "Course saved successfully!", Toast.LENGTH_SHORT).show();
                System.out.println("123" + getContext().getFilesDir());
                // 2. navigate to previous page
                ((MainActivity) getActivity()).navigateBack();
            }
        });
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), courses);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }

    private File getDataFile () {
        return new File(getContext().getFilesDir(), "course.txt");
    }
}