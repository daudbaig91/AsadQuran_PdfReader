package com.example.asadquran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class surahfragmentadapter extends ArrayAdapter {

    List<surahclass> lists;
    Context context;
    int resource;

    public surahfragmentadapter(Context context, int resource, List lists) {
        super(context, resource, lists);

        this.context = context;
        this.resource = resource;
        this.lists = lists;


    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView surahs = view.findViewById(R.id.surahtext);

        TextView numbers = view.findViewById(R.id.number);


        surahs.setText("Surah "+String.valueOf((lists.get(position).number)));
        numbers.setText((String.valueOf(position + 1)));


        return view;


    }



}