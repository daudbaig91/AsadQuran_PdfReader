package com.example.asadquran;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class bookmarkFragment extends Fragment {

    public static List<bookmarklist> marklist = new ArrayList<>();
    public static bookmarktadapterList adapter;
    ListView list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view =  inflater.inflate(R.layout.fragment_bookmark, container, false);


        list = (ListView) view.findViewById(R.id.bookmarklistview);

        ObjectInputStream input;
        String filename = "bookmark.srl";

        try {
            input = new ObjectInputStream(new FileInputStream(new File(new File(getActivity().getFilesDir(), "") + File.separator + filename)));
            marklist = ( List<bookmarklist>) input.readObject();


            input.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        adapter = new bookmarktadapterList(getActivity(), R.layout.bookmark_listview, marklist);

        //attaching adapter to the listview
        list.setAdapter(adapter);




        FloatingActionButton addbookmark = (FloatingActionButton) getActivity().findViewById(R.id.bookmarkButton);
        addbookmark.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //then we will inflate the custom alert dialog xml that we created
                View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.bookmarkdialog, container, false);
                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);
                //finally creating the alert dialog and displaying it
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                EditText parah = ((EditText) dialogView.findViewById(R.id.inputdialogparah2));
                EditText page = ((EditText) dialogView.findViewById(R.id.inputdialogpage));
                EditText surah = ((EditText) dialogView.findViewById(R.id.surahinputdialog));
                EditText verse = ((EditText) dialogView.findViewById(R.id.inputdialogverse));
                EditText title = ((EditText) dialogView.findViewById(R.id.inputdialogTitle));
                EditText description = ((EditText) dialogView.findViewById(R.id.inputdialogdescription));
                Button buttonInsert = (Button) dialogView.findViewById(R.id.insertbuttonbookmark);

                buttonInsert.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Toast.makeText(getActivity(), "Bookmark with"+ surah.getText().toString()+" page"+page.getText().toString()+" added",
                                Toast.LENGTH_SHORT).show();


                        bookmarklist object = new bookmarklist(surah.getText().toString(),parah.getText().toString(),verse.getText().toString(),page.getText().toString(),
                                title.getText().toString(),description.getText().toString());
                        marklist.add(object);
                        String filename = "bookmark.srl";
                        ObjectOutput out = null;

                        try {
                            out = new ObjectOutputStream(new FileOutputStream(new File(getActivity().getFilesDir(),"")+File.separator+filename));
                            out.writeObject(marklist);
                            out.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        adapter.notifyDataSetChanged();
                        alertDialog.cancel();
                    }});

            } });





        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                bookmarklist hero = marklist.get(position);

                openActivitypara1(hero.getPage());

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                bookmarklist hero = marklist.get(pos);
                hero =null;
                marklist.remove(pos);
                String filename = "bookmark.srl";
                ObjectOutput out = null;

                try {
                    out = new ObjectOutputStream(new FileOutputStream(new File(getActivity().getFilesDir(),"")+File.separator+filename));
                    out.writeObject(marklist);
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return view;




}

    public void openActivitypara1(String position) {
        Intent intent = new Intent(getActivity(), quranpages.class);
        intent.putExtra("position", (String.valueOf(Integer.valueOf(position)-2)));
        startActivity(intent);
    }

}