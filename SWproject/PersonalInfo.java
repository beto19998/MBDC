package com.example.bloodbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PersonalInfo extends AppCompatActivity {
    private EditText ID,Name,Age,Phone;
    private TextView Date ;
    private  FirebaseDatabase db=FirebaseDatabase.getInstance();
    private DatabaseReference root=db.getReference().child("PersonalInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        Button button= findViewById( R.id.button2);
        //for Gender Spinner
        List<String> gender= Arrays.asList("Gender","Male","Female");
        final Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //for Location Spinner
        List<String> Location=Arrays.asList("Location","Jerusalem","Ramallaha","Gaza","Bethlehem","Nablus","Hebron","Tullkarm","Jenin");
        final Spinner spinner1=findViewById(R.id.spinner1);
        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,Location);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        button.setOnClickListener(v -> {
            if (!validateId()|!validateName()|!validateAge()|!validatePhone()){
                return;
            }
            Intent intent = new Intent(this,HealthInfo.class);
            startActivity(intent);
            String name =Name.getText().toString().trim();
            String id =ID.getText().toString().trim();
            String age =Age.getText().toString().trim();
            String phone =Phone.getText().toString().trim();
         // String date= Date.getText().toString();
           String item= spinner.getSelectedItem().toString();
            String item1= spinner1.getSelectedItem().toString();

            HashMap<String, String> Personalinfo = new HashMap<>();
            Personalinfo.put("Name",name);
            Personalinfo.put("ID",id);
            Personalinfo.put("Age",age);
            Personalinfo.put("Phone",phone);
          //  Personalinfo.put("Date",date);
            Personalinfo.put("Gender",item);
            Personalinfo.put("Location",item1);
         //  Personalinfo.put("Time",ServerValue.TIMESTAMP);
            root.push().setValue(Personalinfo);

        });
        //current date
        TextView datetime = findViewById(R.id.date);
        Date timenow= Calendar.getInstance().getTime();
        datetime.setText(timenow.toString());
        //get values of edit text
        ID=(EditText)findViewById(R.id.idnum);
        Name=(EditText)findViewById(R.id.name);
        Age=(EditText)findViewById(R.id.age);
        Phone=(EditText)findViewById(R.id.phone);
    }

    private boolean validateName() {
        String name = Name.getText().toString();
        if (name.isEmpty()) {
            Name.setError("Name Required");
            Name.requestFocus();
            return false;
        } else {
            Name.setError(null);
            return true;
        }

    }
    private boolean validateAge() {
        String age = Age.getText().toString();
        int value = 0;
        value= Integer.parseInt(age);

        if (age.isEmpty()) {
            Age.setError("Age Required");
            Age.requestFocus();
            return false;}

        else if (value < 18 | value > 65){
            Age.setError("you are under Age");
            Age.requestFocus();
             displayMessage();
            return false;
        }
        else {
            Age.setError(null);
            return true;
        }

    }
    private void displayMessage() {
        // final Handler handler = new Handler();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sorry you can't donate ^_^, Your Age Out Of The Range 'Click YES to EXIT '  ")
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
     /*   handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },5000);

    }*/
    private boolean validateId(){
        String id = ID.getText().toString();
        if (id.isEmpty()){
            ID.setError("ID Required");
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
    private boolean validatePhone(){
        String phone =Phone.getText().toString();
        if (phone.isEmpty()){
            Phone.setError("Phone number Required");
            Phone.requestFocus();
            return false;
        }
        else if (phone.length() < 10){
            Phone.setError("Your Phone number not complete");
            Phone.requestFocus();
            return false;
        }

        else{
            Phone.setError(null);
            return true;
        }

    }
/*   private boolean validateGender(){
       List<String> gender= Arrays.asList("Gender","Male","Female");
       final Spinner spinner=findViewById(R.id.spinner);
       ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,gender);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(adapter);

      String item= spinner.getSelectedItem().toString();
        if (item.equals("Gender")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Select Your Gender");
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
        return true;
    }*/
}