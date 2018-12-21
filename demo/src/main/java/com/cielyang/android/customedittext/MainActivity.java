package com.cielyang.android.customedittext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cielyang.android.clearableedittext.ClearableEditText;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClearableEditText prepopulatedEditText = findViewById(R.id.prepopulated_edit_text);
        prepopulatedEditText.setText("This is pre-populated and has a clear icon");
    }
}
