package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= findViewById(R.id.button4);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this,PersonalInfo.class);
            startActivity(intent);
        });
    }
}