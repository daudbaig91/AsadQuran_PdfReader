package com.example.asadquran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class quranpages extends AppCompatActivity {

        PDFView pdf;
        Boolean checkClickAnimation = false;
        protected void onCreate (Bundle savedInstanceState) {

           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_quranpages);
           Intent secondIntent = getIntent();
           String message = secondIntent.getStringExtra("position");
           pdf = findViewById(R.id.pdfView);
           pdf.fromAsset("parah1.pdf").spacing(10).defaultPage(Integer.valueOf(message))
                   .load();


        }

        public void searchOpen(View view){
            SearchView sv = findViewById(R.id.searchviewquran);
            sv.setBackgroundColor(Color.parseColor("#489855"));

            if(sv.getVisibility() == View.VISIBLE){
                Log.d("tst","1");
                sv.setVisibility(View.GONE);
            }else {
                Log.d("tst","2");
                sv.setVisibility(View.VISIBLE);
                sv.setIconified(false);
                sv.requestFocus();
            }

            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    sv.setVisibility(view.GONE);
                    jumpTo(Integer.valueOf(query));
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            sv.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View view, boolean has_focus) {
                    if (!has_focus) {
                        sv.setVisibility(view.GONE);
                    }
                }
            });
        }

        public void jumpTo(int pageNumb){
            pdf.jumpTo(pageNumb,true);
        }

        @Override
        protected void onPause () {
            super.onPause();
            saveData();
        }

        protected void onDestroy () {
            super.onDestroy();
            saveData();
        }

        public void saveData(){
            Integer page = pdf.getPageCount();
            SharedPreferences sharedPref = this.getSharedPreferences("application", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("position", page.toString()).apply();
        }

        public void openBttnAnimation (View view) {
            Animation rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open);
            Animation rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close);
            Animation fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom);
            Animation toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom);
            if (checkClickAnimation == true) {
                checkClickAnimation = false;
            } else {
                checkClickAnimation = true;
            }

            FloatingActionButton bookmarkButton1 = (FloatingActionButton) findViewById(R.id.bookmarkButton2);

            FloatingActionButton searchButton3 = (FloatingActionButton) findViewById(R.id.searchButton2);
            FloatingActionButton addSurah3 = (FloatingActionButton) findViewById(R.id.addSurah2);
            FloatingActionButton optionButton = (FloatingActionButton) findViewById(R.id.buttonoption2);
            if (checkClickAnimation == true) {
                bookmarkButton1.setVisibility(View.VISIBLE);

                searchButton3.setVisibility(View.VISIBLE);
                addSurah3.setVisibility(View.VISIBLE);

                bookmarkButton1.setClickable(true);

                searchButton3.setClickable(true);
                addSurah3.setClickable(true);

                bookmarkButton1.startAnimation(fromBottom);

                addSurah3.startAnimation(fromBottom);
                optionButton.startAnimation(rotateOpen);

            } else if (checkClickAnimation == false) {
                bookmarkButton1.setVisibility(View.INVISIBLE);

                searchButton3.setVisibility(View.INVISIBLE);
                addSurah3.setVisibility(View.INVISIBLE);

                bookmarkButton1.setClickable(false);

                searchButton3.setClickable(false);
                addSurah3.setClickable(false);

                bookmarkButton1.startAnimation(toBottom);

                searchButton3.startAnimation(toBottom);
                addSurah3.startAnimation(toBottom);
                optionButton.startAnimation(rotateClose);
            }
        }

        public void bookmarkAdd (View view){

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.bookmarkdialog, null);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                bookmarkFragment.marklist.add(new bookmarklist(surah.getText().toString(), parah.getText().toString(), verse.getText().toString(), page.getText().toString(),
                        title.getText().toString(), description.getText().toString()));
                String filename = "bookmark.srl";
                ObjectOutput out = null;

                try {
                    out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), "") + File.separator + filename));
                    out.writeObject(bookmarkFragment.marklist);
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bookmarkFragment.adapter.notifyDataSetChanged();
                alertDialog.cancel();
            }
        });
    }

        public void surahAdd(View view){
        View dialogView = LayoutInflater.from(this).inflate(R.layout.addingdialog, null);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button buttonInsert = (Button) dialogView.findViewById(R.id.insertid);


        View view3 = LayoutInflater.from(this).inflate(R.layout.fragment_surah, null);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String surahNumber = ((EditText) dialogView.findViewById(R.id.surahn)).getText().toString();
                String pageNumber = ((EditText) dialogView.findViewById(R.id.pagen)).getText().toString();

                try {


                    if (!TextUtils.isEmpty(surahNumber) & (!TextUtils.isEmpty(pageNumber))) {
                        if (Integer.valueOf(surahNumber) > 0 & Integer.valueOf(surahNumber) <= 114) {
                            if (Integer.valueOf(pageNumber) >= 0 & Integer.valueOf(pageNumber) <= 604) {
                                if (!surahFragment.list.contains("Surah " + surahNumber)) {

                                    String filename = "surahs.srl";
                                    ObjectOutput out = null;
                                    Toast.makeText(quranpages.this, quranpages.this.getFilesDir().toString(),
                                            Toast.LENGTH_SHORT).show();
                                    try {
                                        out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), "") + File.separator + filename));
                                        out.writeObject(surahFragment.list);

                                        out.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    ListView listView = (ListView) view3.findViewById(R.id.surahlist);

                                    final surahfragmentadapter monthAdapter = new surahfragmentadapter(getApplicationContext(), R.layout.testlayout, surahFragment.list);
                                    listView.setAdapter(monthAdapter);
                                    surahFragment.monthAdapter.notifyDataSetChanged();

                                } else {
                                    Toast.makeText(quranpages.this, "Surah number already exists! Please remove existing surah",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(quranpages.this, "Wrong numbers, Page Range from 0-604",
                                        Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(quranpages.this, "Surahs are between 1 and 114",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (TextUtils.isEmpty(surahNumber) & !TextUtils.isEmpty(pageNumber)) {
                            Toast.makeText(quranpages.this, "Please Specify the Surah Number",
                                    Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(pageNumber) & !TextUtils.isEmpty(surahNumber)) {
                            Toast.makeText(quranpages.this, "Please Specify the Page Number",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(quranpages.this, "Please Enter a Value",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception e) {
                    Toast.makeText(quranpages.this, "Please enter numbers only or right values!",
                            Toast.LENGTH_SHORT).show();
                }
                alertDialog.cancel();
            }
        });

    }

}































