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
import com.example.studywithkitten.components.Habit;
import com.example.studywithkitten.fragments.ScheduleFragment;
import com.example.studywithkitten.fragments.TodoFragment;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HabitEditFragment extends Fragment {

    EditText habit;

    Button btnSave;

    List<String> habits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_schedule_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        habit = view.findViewById(R.id.habit);
        btnSave = view.findViewById(R.id.btnSave);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Habit");


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Habit newHabit = new Habit();
                newHabit.setHabit(habit.getText().toString());

                loadItems();
                habits.add(newHabit.toString());


                // 1. save info to data.txt
                saveItems();
                Toast.makeText(getActivity(), "Habit saved successfully!", Toast.LENGTH_SHORT).show();
                System.out.println("123" + getContext().getFilesDir());
                // 2. navigate to previous page
                ((MainActivity) getActivity()).navigateBack();
            }
        });
    }

    private void loadItems() {
        try {
            habits = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            habits = new ArrayList<>();
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), habits);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }

    private File getDataFile () {
        return new File(getContext().getFilesDir(), "habit.txt");
    }
}