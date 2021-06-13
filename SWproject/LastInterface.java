package com.example.bloodbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LastInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_interface);
        Button button= findViewById(R.id.button);
     button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             AlertDialog.Builder builder = new AlertDialog.Builder(LastInterface.this);
             builder.setMessage("Are You Sure YOU Want Exit ?")
                     .setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Intent intent = new Intent(Intent.ACTION_MAIN);
                     intent.addCategory(Intent.CATEGORY_HOME);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                 }
             }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
                 }
             });
             AlertDialog dialog = builder.create();
             dialog.show();


         }
     });

    }
}