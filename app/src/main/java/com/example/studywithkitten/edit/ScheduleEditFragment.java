package com.example.studywithkitten.edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ScheduleEditFragment extends Fragment {

    EditText title;
    EditText number;
    EditText date;
    EditText time;
    EditText location;

    Button btnSave;

    List<String> courses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_edit, container, false);
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


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course course = new Course();
                course.setTitle(title.getText().toString());
                course.setNumber(number.getText().toString());
                course.setDate(date.getText().toString());
                course.setTime(time.getText().toString());
                course.setLocation(location.getText().toString());

                loadItems();
                courses.add(course.toString());


                // 1. save info to data.txt
                saveItems();
                Toast.makeText(getActivity(), "Course saved successfully!", Toast.LENGTH_SHORT).show();
                System.out.println("123" + getContext().getFilesDir());
                // 2. navigate to previous page
                Fragment f = new ScheduleFragment();
                ((MainActivity) getActivity()).navigateBack(f);
            }
        });
    }

    private void loadItems() {
        try {
            courses = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            courses = new ArrayList<>();
        }
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