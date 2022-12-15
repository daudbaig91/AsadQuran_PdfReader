package com.example.asadquran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import androidx.annotation.NonNull;

public  class bookmarktadapterList extends ArrayAdapter<bookmarklist> {

    List<bookmarklist> lists;
    Context context;
    int resource;

    public bookmarktadapterList(Context context, int resource,List<bookmarklist> lists) {
        super(context, resource,lists);

        this.context = context;
        this.resource = resource;
        this.lists = lists;


    }

    public  View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView surahs = (TextView) view.findViewById(R.id.Surahbookmark);
        TextView parahs = (TextView) view.findViewById(R.id.parahbookmark);
        TextView verses =(TextView)  view.findViewById(R.id.versebookmark);
        TextView pages = (TextView) view.findViewById(R.id.pagebookmark);
        TextView titles = (TextView) view.findViewById(R.id.titlebookmark);
        TextView descriptions =(TextView)  view.findViewById(R.id.descriptionbookmark);

        bookmarklist hero = lists.get(position);

        surahs.setText("Surah: "+hero.getSurah());
        parahs.setText("Parah: "+hero.getParah());
        verses.setText("Verse: "+hero.getVerse());
        pages.setText("Page: "+ hero.getPage());
        titles.setText("Title:"+hero.getTitle());
        descriptions.setText("Description:\n" +hero.getDescription());

        return view;


    }

    }
