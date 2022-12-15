package com.example.asadquran;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class surahFragment extends Fragment {

    public static surahfragmentadapter monthAdapter;
    Integer pageNumber = null;
    Integer surahNumber = null;
    public static List<surahclass> list = new ArrayList<>();;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


                //save file




        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_surah, container, false);
        ListView listView = (ListView) view.findViewById(R.id.surahlist);
        ObjectInputStream input;
        String filename = "surahs.srl";
        try {
            input = new ObjectInputStream(new FileInputStream(new File(new File(getActivity().getFilesDir(), "") + File.separator + filename)));
            list = ( List<surahclass>) input.readObject();


            input.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //setting the listview
        monthAdapter = new surahfragmentadapter(getActivity(), R.layout.testlayout, list);
        listView.setAdapter(monthAdapter);


        FloatingActionButton addSurah3 = (FloatingActionButton) getActivity().findViewById(R.id.addSurah);
        addSurah3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //then we will inflate the custom alert dialog xml that we created
                View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.addingdialog, container, false);
                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);
                //finally creating the alert dialog and displaying it
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button buttonInsert = (Button) dialogView.findViewById(R.id.insertid);


                buttonInsert.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        String s = ((EditText) dialogView.findViewById(R.id.surahn)).getText().toString();
                        String s1 = ((EditText) dialogView.findViewById(R.id.pagen)).getText().toString();
                        surahNumber = Integer.valueOf(s);
                        pageNumber = Integer.valueOf(s1);


                        try {


                            if (!TextUtils.isEmpty(surahNumber.toString()) & (!TextUtils.isEmpty(pageNumber.toString()))) {
                                if (Integer.valueOf(surahNumber) > 0 & Integer.valueOf(surahNumber) <= 114) {
                                    if (Integer.valueOf(pageNumber) >= 0 & Integer.valueOf(pageNumber) <= 604) {
                                        if (!list.contains("Surah " + surahNumber)) {
                                            addtolist();
                                            monthAdapter.notifyDataSetChanged();
                                        } else {
                                            Toast.makeText(getActivity(), "Surah number already exists! Please remove existing surah",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Wrong numbers, Page Range from 0-604",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    monthAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getActivity(), "Surahs are between 1 and 114",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (TextUtils.isEmpty(surahNumber.toString()) & !TextUtils.isEmpty(pageNumber.toString())) {
                                    Toast.makeText(getActivity(), "Please Specify the Surah Number",
                                            Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(pageNumber.toString()) & !TextUtils.isEmpty(surahNumber.toString())) {
                                    Toast.makeText(getActivity(), "Please Specify the Page Number",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Please Enter a Value",
                                            Toast.LENGTH_SHORT).show();
                                }


                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Please enter numbers only or right values!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.cancel();
                    }
                });


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                surahclass hero = list.get(position);

                openActivitypara1(hero.page.toString());



            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                String buttonclicked1 = arg0.getItemAtPosition(pos).toString();

                list.remove(pos);

                String filename = "surahs.srl";
                ObjectOutput out = null;

                try {
                    out = new ObjectOutputStream(new FileOutputStream(new File(getActivity().getFilesDir(),"")+File.separator+filename));
                    out.writeObject(list);
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                monthAdapter.notifyDataSetChanged();
                return true;
            }
        });


        return view;


    }

    ;

    public void addtolist() {
        surahclass surah = new surahclass(surahNumber,pageNumber);
        list.add(surah);
        sort();

        String filename = "surahs.srl";
        ObjectOutput out = null;


        try {
            out = new ObjectOutputStream(new FileOutputStream(new File(getActivity().getFilesDir(),"")+File.separator+filename));
            out.writeObject(list);
            Toast.makeText(getActivity(),"surah" +surahNumber +" page"+pageNumber+" added",
                    Toast.LENGTH_SHORT).show();

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        monthAdapter.notifyDataSetChanged();

    }


    public void sort() {

        Collections.sort(list);

        }




    public void openActivitypara1(String position) {
        Intent intent = new Intent(getActivity(), quranpages.class);
        intent.putExtra("position", (String.valueOf(Integer.valueOf(position)-2)));
        startActivity(intent);
    }
}



