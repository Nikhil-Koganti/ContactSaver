package com.example.vijay.contactsaver;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by vijay on 30-05-17.
 */

public class ViewContact extends AppCompatActivity {
    EditText name,phone,email;
    int rowid2;
     int a=0;
    Spinner type;
    Button update;
    DatabaseStore database;
    int typo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable (Color.parseColor("#000000")));
        database = new DatabaseStore(this);
        database.openDatabase();
        name = (EditText)findViewById(R.id.id_edtName);
        phone = (EditText)findViewById(R.id.id_edtPhone);
        email = (EditText)findViewById(R.id.id_edtEmail);
        type = (Spinner)findViewById(R.id.id_spinPhone);
        update = (Button)findViewById(R.id.id_btnSave);
        Intent screen = getIntent();
         a = screen.getIntExtra("rowid",0);
        Log.e("rowid in viewcontact",a+"");
        final Cursor curs1 = database.getSingleRow(a);
        final boolean b = curs1.moveToFirst();
//        final String rowd = curs1.getString(curs1.getColumnIndex("id_row"));
        name.setText(curs1.getString(curs1.getColumnIndex("Name")));
//        rowid2 = curs1.getInt(curs1.getColumnIndex("id_row"));
        final String star[] = {"(none)","Mobile","Landline","Fax","Other"};
        ArrayAdapter<String> aradap = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, star);
        type.setAdapter(aradap);

        String str=curs1.getString(curs1.getColumnIndex("Spin"));
        for(int i=0;i<star.length;i++){
            if(str.equals(star[i])){
                typo=i;
            }
        }


        type.setSelection(typo);

        phone.setText(curs1.getString(curs1.getColumnIndex("Phone")));
        email.setText(curs1.getString(curs1.getColumnIndex("Email")));
        update.setText("Update");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.editRecords(a,name.getText().toString(),phone.getText().toString(),email.getText().toString(),type.getSelectedItem().toString());
            Intent intent = new Intent(ViewContact.this,MainActivity.class);

                startActivity(intent);

                //finish();
            }
        });
    }
}
