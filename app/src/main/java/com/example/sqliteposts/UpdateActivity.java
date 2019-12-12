package com.example.sqliteposts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {

    private int postId;

    TextView linkList;
    TextView errorMessage;

    EditText editTitle;
    EditText editBody;

    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editTitle = findViewById(R.id.editTitle);
        editBody = findViewById(R.id.editBody);

        btnUpdate = findViewById(R.id.btnUpdate);

        linkList = findViewById(R.id.linkList);
        errorMessage = findViewById(R.id.errorMessage);

        Bundle extras = getIntent().getExtras();
        postId = extras.getInt("postId");

        Cursor c = MainActivity.db.rawQuery("SELECT * FROM posts WHERE id = ?", new String[] {String.valueOf(postId)});
        if (c.moveToFirst()) {
            editTitle.setText(c.getString(c.getColumnIndex("title")));
            editBody.setText(c.getString(c.getColumnIndex("body")));
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String body = editBody.getText().toString();

                ContentValues updateValues = new ContentValues();
                updateValues.put("title", title);
                updateValues.put("body", body);
                MainActivity.db.update("posts", updateValues, "id = ?", new String[] {String.valueOf(postId)});
            }
        });

        linkList.setMovementMethod(LinkMovementMethod.getInstance());
        linkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, RecyclerActivity.class);
                startActivity(intent);
            }
        });


    }
}
