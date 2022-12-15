package com.example.asadquran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class parahfragmentadapter extends ArrayAdapter {

    String[] lists;
    Context context;
    int resource;

    public parahfragmentadapter(Context context, int resource, String[] lists) {
        super(context, resource, lists);

        this.context = context;
        this.resource = resource;
        this.lists = lists;


    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView surahs = (TextView) view.findViewById(R.id.surahtext);

        TextView numbers = (TextView) view.findViewById(R.id.number);



        surahs.setText(lists[position]);
        numbers.setText((String.valueOf(position+1)));




        return view;


    }



}