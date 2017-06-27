package com.example.vijay.contactsaver;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.name;
import static android.R.attr.type;

public class MainActivity extends AppCompatActivity {
    DatabaseStore database;
    Cursor cursor;
    ListView list;
    Button deleteAll;
//    New_Contact Newct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        database = new DatabaseStore(this);
        database.openDatabase();
        list = (ListView)findViewById(R.id.id_list);
        deleteAll = (Button)findViewById(R.id.id_btndelete);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Do You want to delete all records");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.deleteAllRecords();
                        updateListView();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    updateListView();
                    }
                });
                AlertDialog alertdia = alert.create();
                alertdia.show();
            }
        });
        updateListView();
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                  list.setLongClickable(true);
//                  Log.e("positions is:",cursor.getString(0));
                database.deleteOneRecord(Integer.parseInt(cursor.getString(0)));
               updateListView();
                return true;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent inte = new Intent(MainActivity.this,EditContact.class);
//                startActivity(inte);
                cursor.moveToPosition(position);
                Intent intent = new Intent(MainActivity.this,EditContact.class);
                intent.putExtra("rowid",cursor.getInt(0));
                startActivity(intent);
                Toast.makeText(getApplicationContext(),cursor.getInt(0)+"",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateListView() {
        cursor = database.getAllValues();
        ListAdapter ls = new ListAdapter();
        list.setAdapter(ls);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflate = getMenuInflater();
        menuinflate.inflate(R.menu.activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intnt = new Intent(MainActivity.this,New_Contact.class);
        startActivity(intnt);
        return super.onOptionsItemSelected(item);
    }
    public Cursor getCursor() {
        return cursor;
    }

    class ListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.list_item,null);
            TextView name = (TextView)view.findViewById(R.id.id_txtname);
            TextView phone = (TextView)view.findViewById(R.id.id_txtphone);
            TextView email = (TextView)view.findViewById(R.id.id_txtemail);
            TextView type1= (TextView) view.findViewById(R.id.id_txtspin);
            cursor.moveToPosition(position);
            //int id=cursor.getInt(0);
            String ctname = cursor.getString(1);
            String ctphone = cursor.getString(2);
            String ctemail = cursor.getString(3);
            String cttype=cursor.getString(4);
            if(cttype.equalsIgnoreCase("Mobile"))
            {
                name.setText(ctname);
                phone.setText("+91"+ctphone);
                email.setText(ctemail);
                type1.setText(cttype);
            }
            else if(cttype.equalsIgnoreCase("LandLine"))
            {
                name.setText(ctname);
                phone.setText("(040)"+ctphone);
                email.setText(ctemail);
                type1.setText(cttype);
            }
            else {
                name.setText(ctname);
                phone.setText(ctphone);
                email.setText(ctemail);
                type1.setText(cttype);
            }


            //Log.e("This is: ",name+"\n"+phone+"\n"+email);
            return view;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
