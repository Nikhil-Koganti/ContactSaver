package com.example.vijay.contactsaver;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class New_Contact extends AppCompatActivity {
    EditText nameedt,phoneedt,emailedt;
    Button save;
    Spinner phonespin;
    DatabaseStore database;
    static String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        database = new DatabaseStore(this);
        database.openDatabase();
        nameedt = (EditText)findViewById(R.id.id_edtName);
        phoneedt = (EditText)findViewById(R.id.id_edtPhone);
        emailedt = (EditText)findViewById(R.id.id_edtEmail);
        phonespin = (Spinner)findViewById(R.id.id_spinPhone);
        save = (Button)findViewById(R.id.id_btnSave);


        final String star[] = {"(none)","Mobile","Landline","Fax","Other"};
        ArrayAdapter<String> aradap = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, star);
        phonespin.setAdapter(aradap);
        phonespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    txt=phonespin.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneedt.length()>=10) {
                    database.insertvalues(nameedt.getText().toString(), phoneedt.getText().toString(), emailedt.getText().toString(), txt);
                    Toast.makeText(getApplicationContext(), "Contact Saved", Toast.LENGTH_SHORT).show();
                    Intent oldscreen = new Intent(New_Contact.this, MainActivity.class);
                    startActivity(oldscreen);
                }
                else {
                    Toast.makeText(New_Contact.this, "Please Check No. of digits provided in phone!!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
