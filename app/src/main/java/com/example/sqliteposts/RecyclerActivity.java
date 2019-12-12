package com.example.sqliteposts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class RecyclerActivity extends AppCompatActivity {

    MyAdapter adapter;
    TextView linkAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        linkAdd = findViewById(R.id.linkAdd);
        linkAdd.setMovementMethod(LinkMovementMethod.getInstance());
        linkAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<ItemData> data = new ArrayList<>();

        Cursor selectData = MainActivity.db.query("posts", null, null, null, null, null, null, null);
        if (selectData.moveToFirst()) {
            do {
                int id = selectData.getInt(selectData.getColumnIndex("id"));
                String title = selectData.getString(selectData.getColumnIndex("title"));
                String body = selectData.getString(selectData.getColumnIndex("body"));
                ItemData itemData = new ItemData(id, title, body);
                data.add(itemData);
            } while (selectData.moveToNext());
        }

        RecyclerView recyclerView = findViewById(R.id.rvPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(getApplicationContext(), data);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecor = new DividerItemDecoration(recyclerView.getContext(), VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

    }
}
