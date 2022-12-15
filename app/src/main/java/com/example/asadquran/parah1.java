package com.example.asadquran;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class parah1 extends AppCompatActivity {
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parah1);

        linearLayout = findViewById(R.id.linear_layout);

        for (int i = 1; i <= 21; i++) {
            String x = "parah1_" + Integer.toString(i);
            int drawableId = getResources().getIdentifier(x, "drawable", getPackageName());
            ImageView imageView = new ImageView(this);
            imageView.setAdjustViewBounds(true);
            imageView.setImageResource(drawableId);
            linearLayout.addView(imageView);
        }
    }
}