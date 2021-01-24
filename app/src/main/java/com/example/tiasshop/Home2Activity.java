package com.example.tiasshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Home2Activity extends AppCompatActivity {

    ListView catvieww;
    ArrayAdapter<String> catadapt;
    TiasDB tia;
    int categoriescount;
    String catname ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        catvieww = (ListView) findViewById(R.id.catview);
        tia = new TiasDB(this);
        catadapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        Cursor cursor = tia.getCategries();
        catvieww.setAdapter(catadapt);
        while (!cursor.isAfterLast())
        {
            catadapt.add(cursor.getString(0));
            cursor.moveToNext();
        }
         categoriescount= catvieww.getCount();

            catvieww.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int idd = position;
                    catname=catvieww.getItemAtPosition(position).toString();
                    Cursor catid = tia.getCategriesID(catname);
                    int cat_id = catid.getInt(0);
                    Intent intent = new Intent(Home2Activity.this, ProductsActivity.class);
                    intent.putExtra("catid", idd);
                    intent.putExtra("categid" , cat_id);
                    intent.putExtra("listcount" , categoriescount);
                    startActivity(intent);
                }
            });




        }


}
