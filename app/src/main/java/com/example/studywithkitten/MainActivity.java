package com.example.studywithkitten;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.studywithkitten.fragments.HabitFragment;
import com.example.studywithkitten.fragments.ScheduleFragment;
import com.example.studywithkitten.fragments.StudyModeFragment;
import com.example.studywithkitten.fragments.TodoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_study:
                        Toast.makeText(MainActivity.this, "Home!", Toast.LENGTH_SHORT).show();
                        fragment = new StudyModeFragment();
                        break;
                    case R.id.action_todo:
                        Toast.makeText(MainActivity.this, "Todo!", Toast.LENGTH_SHORT).show();
                        fragment = new TodoFragment();
                        break;
                    case R.id.action_habit:
                        fragment = new HabitFragment();
                        break;
                    case R.id.action_schedule:
                        fragment = new ScheduleFragment();
                        break;
                    default:
                        fragment = new StudyModeFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_study);
    }
}
