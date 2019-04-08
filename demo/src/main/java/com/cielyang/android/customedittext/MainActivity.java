package com.cielyang.android.customedittext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cielyang.android.clearableedittext.ClearableAutoCompleteTextView;
import com.cielyang.android.clearableedittext.ClearableEditText;
import com.cielyang.android.clearableedittext.OnTextClearedListener;

public class MainActivity extends AppCompatActivity implements OnTextClearedListener {

    @SuppressLint("SetTextI18n")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClearableEditText prepopulatedEditText = findViewById(R.id.prepopulated_edit_text);
        prepopulatedEditText.setText("This is pre-populated and has a clear icon");
        prepopulatedEditText.setOnTextClearedListener(this);

        ClearableEditText emptyEditText = findViewById(R.id.edit_text);
        emptyEditText.setOnTextClearedListener(this);

        ClearableAutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete_text_view);
        autoCompleteTextView.setOnTextClearedListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override public void onTextCleared() {
        Toast.makeText(this, "Cleared text", Toast.LENGTH_LONG).show();
    }
}
