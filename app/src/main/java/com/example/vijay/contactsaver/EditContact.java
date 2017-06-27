package com.example.vijay.contactsaver;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class EditContact extends AppCompatActivity {
    TextView name,type,phone,email;
    Cursor cursor;
    DatabaseStore databasest;
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        databasest = new DatabaseStore(this);
        databasest.openDatabase();
        name = (TextView)findViewById(R.id.id_chname);
        type = (TextView)findViewById(R.id.id_chtype);
        phone = (TextView)findViewById(R.id.id_chphone);
        email = (TextView)findViewById(R.id.id_chemail);
        Intent screen = getIntent();
         a = screen.getIntExtra("rowid",0);
        Cursor curs1 = databasest.getSingleRow(a);
        boolean b = curs1.moveToFirst();
        Log.e("boolean value:",b+"");
        Log.e("name is:",curs1.getString(curs1.getColumnIndex("Name")));
        Log.e("mobile",curs1.getString(curs1.getColumnIndex("Phone")));
        Log.e("type",curs1.getString(curs1.getColumnIndex("Spin")));
        Log.e("email",curs1.getString(curs1.getColumnIndex("Email")));
        name.setText(curs1.getString(curs1.getColumnIndex("Name")));
        type.setText(curs1.getString(curs1.getColumnIndex("Spin")));
        phone.setText(curs1.getString(curs1.getColumnIndex("Phone")));
        email.setText(curs1.getString(curs1.getColumnIndex("Email")));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflate = getMenuInflater();
        menuinflate.inflate(R.menu.activity_menu2,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intn = new Intent(EditContact.this,ViewContact.class);
        intn.putExtra("rowid",a);
        Log.e("i am at","edit icon");
        startActivity(intn);
       // finish();
        return super.onOptionsItemSelected(item);
    }
}
