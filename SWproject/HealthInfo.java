package com.example.bloodbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HealthInfo extends AppCompatActivity {
    private EditText  SugarRate,Weight,ID;
    private  FirebaseDatabase db=FirebaseDatabase.getInstance();
    private DatabaseReference root=db.getReference().child("HealthInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info);
        Button button= findViewById(R.id.button3);
        List<String> Pressure= Arrays.asList("CHOOSE","Less than 120/80","120/80","More than 120/80");
        final Spinner spinner2=findViewById(R.id.spinner2);
        ArrayAdapter adapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,Pressure);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        //for Diseases Spinner
        List<String>Diseases=Arrays.asList("CHOOSE","AIDS","Hepatitis","Hereditary Blood Diseases","Cancer","None");
        final Spinner spinner3=findViewById(R.id.spinner3);
        ArrayAdapter adapter3 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,Diseases);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        //for Blood Type Spinner
        List<String>BloodType=Arrays.asList("CHOOSE","A+","A-","B+","B-","O+","O-","AB");
        final Spinner spinner4=findViewById(R.id.spinner4);
        ArrayAdapter adapter4 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,BloodType);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        button.setOnClickListener(v -> {
            if (!validateId()|!validateSugarRate()|!validateWeight()){
                return;
            } else if (spinner2.getSelectedItem().toString().equals("More than 120/80")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Sorry you can't donate ^_^,Your Pressure out of the Range\n'Click YES to EXIT'");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
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
                return ;
            }
            else if (!spinner3.getSelectedItem().toString().equals("None")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Sorry you can't donate ^_^ due to a prohibited disease\n'Click YES to EXIT'");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
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
                return ;
            }
            Intent intent = new Intent(this,LastInterface.class);
            startActivity(intent);
            String sugar =SugarRate.getText().toString().trim();
            String weight =Weight.getText().toString().trim();
            String id = ID.getText().toString().trim();
            String item= spinner2.getSelectedItem().toString();
            String item1= spinner3.getSelectedItem().toString();
            String item2= spinner4.getSelectedItem().toString();

            HashMap<String, String> Healthinfo = new HashMap<>();
            Healthinfo.put("SugarRate",sugar);
            Healthinfo.put("Weight",weight);
            Healthinfo.put("ID",id);
            Healthinfo.put("Pressure",item);
            Healthinfo.put("Diseases",item1);
            Healthinfo.put("Blood Type",item2);
            root.push().setValue(Healthinfo);
        });

        //get edit text variable
        SugarRate=(EditText)findViewById(R.id.sugarrate);
        Weight=(EditText)findViewById(R.id.weight);
        ID = (EditText)findViewById(R.id.confirmID);

    }
    //function to validate
    private boolean validateSugarRate(){
        String sugar =SugarRate.getText().toString();
        int value = Integer.parseInt(sugar);
        if (sugar.isEmpty()){
            SugarRate.setError("Sugar Rate Required");
            SugarRate.requestFocus();
            return false;
        }
        else if ( value>100 | value<70){
            SugarRate.setError(" your Sugar Rate unacceptable");
            SugarRate.requestFocus();
            displayMessage();
            return false;
        }
        else{
            SugarRate.setError(null);
           return true;
        }

    }
    private boolean validateWeight(){
        String weight =Weight.getText().toString();
        int value = Integer.parseInt(weight);
        if (weight.isEmpty()){
            Weight.setError(" Weight Required");
            Weight.requestFocus();
            displayMessage();
            return false;
        }
        else if(value < 50 ){
            Weight.setError("You are under Weight");
            Weight.requestFocus();
            return false;
        }
        else{
            Weight.setError(null);
            return true;
        }

    }
    private boolean validateId(){
        String id = ID.getText().toString();
        if (id.isEmpty()){
            ID.setError("ID  Required");
            ID.requestFocus();
            return false;
        }
        else if (id.length() < 6){
            ID.setError("Your ID not complete");
            ID.requestFocus();
            return false;
        }

        else{
            ID.setError(null);
            return true;
        }

    }
    private void displayMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sorry you can't donate ^_^,Your SugarRate/Weight out of the Range 'Click YES to EXIT'");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
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
   /* private boolean validatePressure(){
        //for Pressure Spinner
        List<String> Pressure= Arrays.asList("Less than 120/80","120/80","More than 120/80");
        final Spinner spinner2=findViewById(R.id.spinner2);
        ArrayAdapter adapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,Pressure);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        String item= spinner2.getSelectedItem().toString();
        if (item.equals("More than 120/80")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Sorry you can't donate ^_^,Your Pressure out of the Range\n'Click YES to EXIT'");
            builder.setCancelable(false);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
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
            return false;
        }
        return true;
    }
    private boolean validateDiseases(){
         List<String>Diseases=Arrays.asList("AIDS","Hepatitis","Hereditary Blood Diseases","Cancer","None");

        final Spinner spinner3=findViewById(R.id.spinner3);
        ArrayAdapter adapter3 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,Diseases);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        String item= spinner3.getSelectedItem().toString();
        if (!item.equals("None")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Sorry you can't donate ^_^ due to a prohibited disease\n'Click YES to EXIT'");
            builder.setCancelable(false);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
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
            return false;
        }
        return true;
    }*/

    }
