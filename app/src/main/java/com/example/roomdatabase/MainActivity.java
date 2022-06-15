package com.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button btAdd,btReset;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    List<MainData> dataList = new ArrayList<>();
    RoomDB database;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);

        //Initialize database
        database = RoomDB.getInstance(this);

        //Store database value in dataList
        dataList = database.mainDao().getAll();

        //Initialize LineraLayout Manager
        linearLayoutManager = new LinearLayoutManager(this);

        //set Layout Manager
        recyclerView.setLayoutManager(linearLayoutManager);

        //Initialize adapter
        mainAdapter = new MainAdapter(MainActivity.this,dataList);

        //set Adapter
        recyclerView.setAdapter(mainAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get string from edit text
                String sText = editText.getText().toString().trim();

                //check condition
                if(!sText.equals("")){

                    //When text is not empty
                    //Initialize main   data

                    MainData data = new MainData();
                    data.setText(sText);

                    //Insert text in database
                    database.mainDao().insert(data);
                    editText.setText("");

                    //Notifay when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    mainAdapter.notifyDataSetChanged();
                }

            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Delete All data from database
                database.mainDao().reset(dataList);

                //Notifay when all data deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                mainAdapter.notifyDataSetChanged();
            }
        });
    }
}