package com.example.studywithkitten.edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.studywithkitten.R;
import com.example.studywithkitten.components.Course;
import com.example.studywithkitten.fragments.ScheduleFragment;
import com.example.studywithkitten.fragments.TodoFragment;

public class ScheduleEditActivity extends Fragment {

    EditText title;
    EditText number;
    EditText date;
    EditText time;
    EditText location;

    Button btnSave;

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
        final Course course = new Course();
        course.setTitle(title.getText().toString());
        course.setNumber(number.getText().toString());
        course.setDate(date.getText().toString());
        course.setTime(time.getText().toString());
        course.setLocation(location.getText().toString());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(ScheduleFragment.KEY_ITEM_TEXT, course.toString());
                intent.putExtra(ScheduleFragment.KEY_ITEM_POSITION, getActivity().getIntent().getExtras().getInt(TodoFragment.KEY_ITEM_POSITION));

                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });
    }
}