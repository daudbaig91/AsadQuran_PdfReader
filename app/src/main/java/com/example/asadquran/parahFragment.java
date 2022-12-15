package com.example.asadquran;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;


public class parahFragment extends Fragment implements AdapterView.OnItemClickListener {


    String[] months = new String[30];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parah, container, false);
        ListView listView = (ListView) view.findViewById(R.id.surahlist);
        for (int i = 1; i <= 30; i++) {
            months[i-1] = "Parah " + Integer.toString(i);
        }

        final parahfragmentadapter monthAdapter = new parahfragmentadapter(getActivity(), R.layout.testlayout, months);
        listView.setAdapter(monthAdapter);
        listView.setOnItemClickListener(this);




        return view;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String buttonclicked = parent.getItemAtPosition(position).toString();

        HashMap<String, String> capitalCities = new HashMap<String, String>();

        capitalCities.put("Parah 1", "0");

        capitalCities.put("Parah 2", "21");

        capitalCities.put("Parah 3", "41");

        capitalCities.put("Parah 4", "61");

        capitalCities.put("Parah 5", "81");

        capitalCities.put("Parah 6", "101");

        capitalCities.put("Parah 7", "121");

        capitalCities.put("Parah 8", "141");

        capitalCities.put("Parah 9", "161");

        capitalCities.put("Parah 10", "181");

        capitalCities.put("Parah 11", "201");

        capitalCities.put("Parah 12", "221");

        capitalCities.put("Parah 13", "241");

        capitalCities.put("Parah 14", "261");

        capitalCities.put("Parah 15", "281");

        capitalCities.put("Parah 16", "301");

        capitalCities.put("Parah 17", "321");

        capitalCities.put("Parah 18", "339");

        capitalCities.put("Parah 19", "359");

        capitalCities.put("Parah 20", "379");

        capitalCities.put("Parah 21", "399");

        capitalCities.put("Parah 22", "419");

        capitalCities.put("Parah 23", "439");

        capitalCities.put("Parah 24", "459");

        capitalCities.put("Parah 25", "479");

        capitalCities.put("Parah 26", "499");

        capitalCities.put("Parah 27", "519");

        capitalCities.put("Parah 28", "539");

        capitalCities.put("Parah 29", "559");

        capitalCities.put("Parah 30", "581");

        openActivitypara1(capitalCities.get(buttonclicked));



    }
    public void openActivitypara1(String position) {




        Intent intent = new Intent(getActivity(), quranpages.class);

        intent.putExtra("position",position);
        startActivity(intent);

    }


}