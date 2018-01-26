package com.example.matthieuroulette.applirandofilm;

/**
 * Created by matthieuroulette on 25/01/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

    private HashMap<String, Integer> mItems;

    MyAdapter(HashMap<String, Integer> items) {
        mItems = items;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String key = (String) mItems.keySet().toArray()[position];
        holder.textView.setText(key);
        holder.imageView.setImageResource(mItems.get(key));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}