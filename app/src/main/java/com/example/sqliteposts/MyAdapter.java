package com.example.sqliteposts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<ItemData> itemsData;
    private Context context;

    public MyAdapter(Context context, ArrayList<ItemData> itemsData) {
        this.itemsData = itemsData;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View itemLayoutView = LayoutInflater.from(context)
                .inflate(R.layout.recycler_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.txtViewTitle.setText(itemsData.get(position).getTitle());
        viewHolder.txtViewBody.setText(itemsData.get(position).getBody());
        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("postId", itemsData.get(position).getId());
                context.startActivity(intent);
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postId = String.valueOf(itemsData.get(position).getId());
                MainActivity.db.delete("posts", "id = ?", new String[] {postId});
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public TextView txtViewBody;
        public Button btnUpdate;
        public Button btnDelete;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            txtViewBody = (TextView) itemLayoutView.findViewById(R.id.item_body);
            btnUpdate = (Button) itemLayoutView.findViewById(R.id.btnUpdate);
            btnDelete = (Button) itemLayoutView.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}