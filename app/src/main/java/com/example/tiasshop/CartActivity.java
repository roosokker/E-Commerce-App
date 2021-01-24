package com.example.tiasshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CartActivity extends AppCompatActivity {

    private static String Pref_Cart = "prefCart";
    private static String Pref_Name = "prefFile";

    ListView cartitemss;
    ArrayList<cartitems> items;
    Integer price , quant;
    String name;
    cartitems cartitems;
    float totalprice = 0;
    TiasDB tia;
    public int quantity = 0;
    TextView tp;
    int prodid;
    String prodName [];
    Integer prodPrice[] , prodQuantity[] , image[] , addquant[] , subquant[] , removprod[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartitemss = (ListView)findViewById(R.id.cartitems);
        cartitems = new cartitems();
        items = new ArrayList<cartitems>();
        tia = new TiasDB(CartActivity.this);
        tp = (TextView)findViewById(R.id.totalprice);
        Cursor cursor = tia.getCartitems();
        int i = 0;
        while (!cursor.isAfterLast())
        {
         cartitems.setName(cursor.getString(0));
         cartitems.setPrice(cursor.getFloat(1));
         cartitems.setQuantity(cursor.getInt(2));
         items.add(i , cartitems);
         totalprice = (totalprice +cartitems.getPrice())*cartitems.getQuantity();
         //image[i] = R.drawable.cloth;
         //removprod[i]=R.drawable.ic_minus;
         //subquant[i] = R.drawable.ic_minus;
         //addquant[i] = R.drawable.ic_add;
         i++;
         cursor.moveToNext();
        }
        String totp = String.valueOf(totalprice);
        tp.setText(totp);

        final CartListAdapter adapter = new CartListAdapter(this,items);
                cartitemss.setAdapter(adapter);


    }

    public void deleteupdatequant()
    {
        Integer availquant = getIntent().getIntExtra("availablequant" , 0);
        prodid = getIntent().getIntExtra("prodid" , 0);
        Integer addedquant = getIntent().getIntExtra("quantadded" , 0);
        float price = getIntent().getFloatExtra("price" , 0);
        availquant+=addedquant;
        tia.updatequant(prodid , availquant);
        totalprice -= price * addedquant;
        tp.setText(String.valueOf(totalprice));
    }
}
