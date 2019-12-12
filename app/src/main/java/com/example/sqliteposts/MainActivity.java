package com.example.sqliteposts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase db = null;
    Button btnPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPosts = (Button) findViewById(R.id.btnPosts);
        btnPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
                startActivity(intent);
            }
        });

        if (db==null) {
            DbHelper sqlHelper = new DbHelper(this);
            db = sqlHelper.getWritableDatabase();

            JsonArrayRequest postArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    "https://jsonplaceholder.typicode.com/posts",
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i=0; i<50; i++){
                                try {
                                    JSONObject item = response.getJSONObject(i);
                                    ContentValues insertValues = new ContentValues();
                                    insertValues.put("title", item.getString("title"));
                                    insertValues.put("body", item.getString("body"));
                                    db.insert("posts", null, insertValues);
                                } catch (JSONException e) { e.printStackTrace(); }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { }
                    }
            );

            RequestQueue newRequestQueue = Volley.newRequestQueue(this);
            newRequestQueue.add(postArrayRequest);
        }

    }
}
