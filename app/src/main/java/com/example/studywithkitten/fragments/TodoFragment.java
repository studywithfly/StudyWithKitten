package com.example.studywithkitten.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.studywithkitten.edit.TodoEditFragment;
import com.example.studywithkitten.edit.TodoItemsAdapter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItem;
    TodoItemsAdapter todoItemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_todo_main, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setContentView(R.layout.activity_todo_main);
        btnAdd = view.findViewById(R.id.btnAdd);
        etItem = view.findViewById(R.id.etItem);
        rvItem = view.findViewById(R.id.rvItem);

        loadItems();

        TodoItemsAdapter.OnLongClickListener onItemLongClicked = new TodoItemsAdapter.OnLongClickListener() {
            public void onItemLongClicked(int position) {
                items.remove(position);
                todoItemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "Items was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        TodoItemsAdapter.OnClickListener onClickListener = new TodoItemsAdapter.OnClickListener() {

            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at position" + position);
                Intent i = new Intent(getContext(), TodoEditFragment.class);
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };

        todoItemsAdapter = new TodoItemsAdapter(items, onItemLongClicked, onClickListener);
        rvItem.setAdapter(todoItemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = etItem.getText().toString();
                items.add(todoItem);
                todoItemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getContext(), "Items was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile () {
        return new File(getContext().getFilesDir(), "data.txt");
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








//package com.example.studywithkitten.fragments;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.studywithkitten.R;
//import com.example.studywithkitten.edit.TodoEditActivity;
//import com.example.studywithkitten.edit.TodoItemsAdapter;
//
//import org.apache.commons.io.FileUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TodoFragment extends Activity {
//    public static final String KEY_ITEM_TEXT = "item_text";
//    public static final String KEY_ITEM_POSITION = "item_position";
//    public static final int EDIT_TEXT_CODE = 20;
//
//    List<String> items;
//
//    Button btnAdd;
//    EditText etItem;
//    RecyclerView rvItem;
//    TodoItemsAdapter todoItemsAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_todo_main);
//
//
//        btnAdd = findViewById(R.id.btnAdd);
//        etItem = findViewById(R.id.etItem);
//        rvItem = findViewById(R.id.rvItem);
//
//        loadItems();
//
//        TodoItemsAdapter.OnLongClickListener onItemLongClicked = new TodoItemsAdapter.OnLongClickListener() {
//            public void onItemLongClicked(int position) {
//                items.remove(position);
//                todoItemsAdapter.notifyItemRemoved(position);
//                Toast.makeText(getApplicationContext(), "Items was removed", Toast.LENGTH_SHORT).show();
//                saveItems();
//            }
//        };
//
//
//        TodoItemsAdapter.OnClickListener onClickListener = new TodoItemsAdapter.OnClickListener() {
//
//            @Override
//            public void onItemClicked(int position) {
//                Log.d("MainActivity", "Single click at position" + position);
//                Intent i = new Intent(TodoFragment.this, TodoEditActivity.class);
//                i.putExtra(KEY_ITEM_TEXT, items.get(position));
//                i.putExtra(KEY_ITEM_POSITION, position);
//                startActivityForResult(i, EDIT_TEXT_CODE);
//            }
//        };
//
//        todoItemsAdapter = new TodoItemsAdapter(items, onItemLongClicked, onClickListener);
//        rvItem.setAdapter(todoItemsAdapter);
//        rvItem.setLayoutManager(new LinearLayoutManager(this));
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String todoItem = etItem.getText().toString();
//                items.add(todoItem);
//                todoItemsAdapter.notifyItemInserted(items.size()-1);
//                etItem.setText("");
//                Toast.makeText(getApplicationContext(), "Items was added", Toast.LENGTH_SHORT).show();
//                saveItems();
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode ==RESULT_OK && requestCode == EDIT_TEXT_CODE) {
//            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
//            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
//            items.set(position, itemText);
//            todoItemsAdapter.notifyItemChanged(position);
//            saveItems();
//            Toast.makeText(getApplicationContext(), "Item uodated successfully", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.w("MainActivity", "Unknown call to onActivityResult");
//        }
//    }
//
//    private File getDataFile () {
//        return new File(getFilesDir(), "data.txt");
//    }
//
//
//    private void loadItems() {
//        try {
//            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
//        } catch (IOException e) {
//            Log.e("MainActivity", "Error reading items", e);
//            items = new ArrayList<>();
//        }
//
//    }
//
//    private void saveItems(){
//        try {
//            FileUtils.writeLines(getDataFile(), items);
//        } catch (IOException e) {
//            Log.e("MainActivity", "Error writing items", e);
//        }
//    }
//}