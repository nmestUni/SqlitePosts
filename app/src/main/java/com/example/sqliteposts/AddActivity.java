package com.example.sqliteposts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    TextView linkList;
    TextView errorMessage;

    EditText editTitle;
    EditText editBody;

    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTitle = findViewById(R.id.editTitle);
        editBody = findViewById(R.id.editBody);

        btnAdd = findViewById(R.id.btnAdd);

        linkList = findViewById(R.id.linkList);
        errorMessage = findViewById(R.id.errorMessage);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String body = editBody.getText().toString();

                if (TextUtils.isEmpty(editTitle.getText())
                        || TextUtils.isEmpty(editBody.getText())) {
                    errorMessage.setText("All Fields are mandatory");
                } else {
                    ContentValues insertValues = new ContentValues();
                    insertValues.put("title", title);
                    insertValues.put("body", body);
                    MainActivity.db.insert("posts", null, insertValues);
                    Toast.makeText(getApplicationContext(),"Successfully added",Toast.LENGTH_SHORT).show();
                }
            }
        });

        linkList.setMovementMethod(LinkMovementMethod.getInstance());
        linkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, RecyclerActivity.class);
                startActivity(intent);
            }
        });
    }
}
