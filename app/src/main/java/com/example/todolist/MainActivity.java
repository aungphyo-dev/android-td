package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
public class MainActivity extends AppCompatActivity {
    ListView lstViewTasks;
    DBHelper db;
    TaskAdapter adapter;
    EditText edtNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtNew=findViewById(R.id.edtnNewTask);
        ImageButton btnNew=findViewById(R.id.btnNew);
        db=new DBHelper(MainActivity.this);
        lstViewTasks=(ListView) findViewById(R.id.listViewTasks);
// Initialize the adapter with an empty data array initially
                adapter = new TaskAdapter(this, R.layout.activity_tasks,
                TaskAdapter.data);
// Set the adapter on the ListView
        lstViewTasks.setAdapter(adapter);
// Load data from the database
        selectData();
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean
                        check=db.insertTaskData(edtNew.getText().toString());
                adapter.clear();
                selectData();
            }
        });
    }
    public void selectData(){// Retrieve data from the database
        Cursor cursor = db.selectData();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String task =
                        cursor.getString(cursor.getColumnIndexOrThrow("task"));
                int status =
                        cursor.getInt(cursor.getColumnIndexOrThrow("status"));
                boolean s=status==1?true:false;
                TaskAdapter.data.add(new TaskInfo(task, s));
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter = new TaskAdapter(this, R.layout.activity_tasks,
                TaskAdapter.data);
        lstViewTasks.setAdapter(adapter);
    }
}