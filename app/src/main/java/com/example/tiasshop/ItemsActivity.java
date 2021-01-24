package com.example.tiasshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ItemsActivity extends AppCompatActivity {

    ImageView icon;
    TextView name , price , quant , quantadded;
    ImageButton add, cart;
    private static String Pref_Name = "prefFile";
    private static String Pref_Cart = "prefCart";
    TiasDB tia;
    int n =0 ;
    int   q ;
    int id = 0;
    float pric;
    String nam;
    int catid=0;
    Integer num =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        icon = (ImageView)findViewById(R.id.imageView);
        name = (TextView)findViewById(R.id.name);
        price = (TextView)findViewById(R.id.price);
        quant = (TextView)findViewById(R.id.quant);
        quantadded=(TextView)findViewById(R.id.quantadded);
        add = (ImageButton)findViewById(R.id.addbtn);
        cart = (ImageButton)findViewById(R.id.cart);
        tia = new TiasDB(ItemsActivity.this);

        catid  = getIntent().getIntExtra("categgid",0);
        nam = getIntent().getStringExtra("name");
        pric = getIntent().getFloatExtra("price" , 0);
        q =getIntent().getIntExtra("quant" , 0);
        icon.setImageResource(getIntent().getIntExtra("image" , 0));
        name.setText(nam);
        price.setText(String.valueOf(pric));
        quant.setText(Integer.toString(q));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (q!=0)
                {
                    n++;
                    quantadded.setText(String.valueOf(n));
                    num = Integer.parseInt(quantadded.getText().toString());
                    q--;
                    quant.setText(Integer.toString(q));

                }
                q=q;

            }
        });
        addtocart();


    }

    public void addtocart()
    {
        final String proname = name.getText().toString();
        final float proprice = Float.parseFloat(price.getText().toString());
        //final Integer proquant = Integer.parseInt(quantadded.getText().toString());
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tia.insertintocart(proname , proprice , num);
                Cursor proid = tia.getProductID(proname, proprice , catid);
                while(!proid.isAfterLast())
                {
                    id = proid.getInt(0);
                    proid.moveToNext();
                }
                tia.updatequant(id , q);
                Toast.makeText(ItemsActivity.this , "item is added to cart" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backprod(View view) {
        Intent intent = new Intent(this , ProductsActivity.class);
        startActivity(intent);
    }

    public void showcart(View view) {
        Intent intent = new Intent(this , CartActivity.class);
        intent.putExtra("availablequant" , q);
        intent.putExtra("prodid" , id);
        intent.putExtra("quantadded" , num);
        intent.putExtra("price" , pric);
        startActivity(intent);
    }
}
