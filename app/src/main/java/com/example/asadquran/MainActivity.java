package com.example.asadquran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormatSymbols;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutManager;

    Boolean checkClickAnimation = false;


    private Button button;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SearchView svg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
        }

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        tabLayout.setupWithViewPager((viewPager));


        svg = (SearchView)findViewById(R.id.search);
        svg.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    if (Integer.valueOf(query) >= 2 & Integer.valueOf(query) <= 604){
                        openActivitypara1(String.valueOf(Integer.valueOf(query)-2));}
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Wrong numbers, Page Range from 1-604",
                            Toast.LENGTH_SHORT).show();
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;

            }
        });



        VPAdapter vpAdapter  = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdapter.addFragment(new parahFragment(), "Parah");
        vpAdapter.addFragment(new surahFragment(), "Surah");
        vpAdapter.addFragment(new bookmarkFragment(), "BookMark");

        viewPager.setAdapter(vpAdapter);





    }

    public void resume(View view){
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);

            openActivitypara1(sharedPref.getString("position", "2"));

    }

    public void search(View view){
        svg = (SearchView)findViewById(R.id.search);
        svg.setIconified(false);
        svg.requestFocus();

    }


    public void animation(View view){
        Animation rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_open);
        Animation rotateClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_close);
        Animation fromBottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_bottom);
        Animation toBottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom);
        if(checkClickAnimation == true){
            checkClickAnimation =false;
        }else {
        checkClickAnimation = true;}

        FloatingActionButton bookmarkButton1 = (FloatingActionButton) findViewById(R.id.bookmarkButton);
        FloatingActionButton resumeButton2 = (FloatingActionButton) findViewById(R.id.resumeButton);
        FloatingActionButton searchButton3 = (FloatingActionButton) findViewById(R.id.searchButton);
        FloatingActionButton addSurah3 = (FloatingActionButton) findViewById(R.id.addSurah);
        FloatingActionButton optionButton = (FloatingActionButton) findViewById(R.id.buttonoption);
        if (checkClickAnimation == true){
            bookmarkButton1.setVisibility(View.VISIBLE);
            resumeButton2.setVisibility(View.VISIBLE);
            searchButton3.setVisibility(View.VISIBLE);
            addSurah3.setVisibility(View.VISIBLE);

            bookmarkButton1.setClickable(true);
            resumeButton2.setClickable(true);
            searchButton3.setClickable(true);
            addSurah3.setClickable(true);

            bookmarkButton1.startAnimation(fromBottom);
            resumeButton2.startAnimation(fromBottom);
            searchButton3.startAnimation(fromBottom);
            addSurah3.startAnimation(fromBottom);
            optionButton.startAnimation(rotateOpen);

        }else if (checkClickAnimation == false){
            bookmarkButton1.setVisibility(View.INVISIBLE);
            resumeButton2.setVisibility(View.INVISIBLE);
            searchButton3.setVisibility(View.INVISIBLE);
            addSurah3.setVisibility(View.INVISIBLE);

            bookmarkButton1.setClickable(false);
            resumeButton2.setClickable(false);
            searchButton3.setClickable(false);
            addSurah3.setClickable(false);

            bookmarkButton1.startAnimation(toBottom);
            resumeButton2.startAnimation(toBottom);
            searchButton3.startAnimation(toBottom);
            addSurah3.startAnimation(toBottom);
            optionButton.startAnimation(rotateClose);
        }


    }







    public void openActivitypara1(String position) {




        Intent intent = new Intent(MainActivity.this, quranpages.class);

        intent.putExtra("position",position);
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this, "granted",
                            Toast.LENGTH_LONG).show();
            }else{
                    Toast.makeText(MainActivity.this, " NOT granted",
                            Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}

