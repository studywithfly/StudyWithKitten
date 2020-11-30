package com.example.studywithkitten.fragments;

import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

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

        import com.example.studywithkitten.R;
import com.example.studywithkitten.components.Course;
import com.example.studywithkitten.edit.ScheduleEditActivity;
import com.example.studywithkitten.edit.ScheduleItemsAdapter;
import com.example.studywithkitten.edit.TodoEditActivity;
        import com.example.studywithkitten.edit.ScheduleItemsAdapter;

        import org.apache.commons.io.FileUtils;

        import java.io.File;
        import java.io.IOException;
        import java.nio.charset.Charset;
        import java.util.ArrayList;
        import java.util.List;

public class ScheduleFragment extends Fragment {
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;

    Button btnAdd;
    RecyclerView rvItem;
    ScheduleItemsAdapter scheduleItemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_schedule_main, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAdd = view.findViewById(R.id.btnAdd);
        rvItem = view.findViewById(R.id.rvItem);

        loadItems();

        ScheduleItemsAdapter.OnLongClickListener onItemLongClicked = new ScheduleItemsAdapter.OnLongClickListener() {
            public void onItemLongClicked(int position) {
                items.remove(position);
                scheduleItemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "Items was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        ScheduleItemsAdapter.OnClickListener onClickListener = new ScheduleItemsAdapter.OnClickListener() {

            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at position" + position);
                Intent i = new Intent(getContext(), TodoEditActivity.class);
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };

        scheduleItemsAdapter = new ScheduleItemsAdapter(items, onItemLongClicked, onClickListener);
        rvItem.setAdapter(scheduleItemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: trigger edit page
                Intent i = new Intent(getContext(), ScheduleEditActivity.class);
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        });
    }

    private File getDataFile () {
        return new File(getContext().getFilesDir(), "courses.txt");
    }


    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}