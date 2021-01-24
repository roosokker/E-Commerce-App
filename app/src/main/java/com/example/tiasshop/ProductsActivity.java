package com.example.tiasshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {


    String prodname;
    Integer Price , quan , img;
    GridView prodview;
    TiasDB tia;
    Integer iconss[]={};
    Products prod;
    int catid , listcount , id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        catid = getIntent().getIntExtra("categid" , 0);
        tia = new TiasDB(ProductsActivity.this);
        prod = new Products();
        listcount = getIntent().getIntExtra("listcount" , 0);
        id = getIntent().getIntExtra("catid" , -1);
        iconss=new Integer[100];
        Cursor cursor = tia.getProduct(catid);
        int prodcount = cursor.getCount();
        final ArrayList<Products> arry = new ArrayList<Products>();
        int j = 0;
            while (!cursor.isAfterLast())
            {
                prod = new Products();
                prod.setName(cursor.getString(0));
                prod.setPrice(cursor.getFloat(1));
                prod.setQuantity(cursor.getInt(2));
                arry.add(j,prod);
                iconss[j] = R.drawable.cloth;
                cursor.moveToNext();
            j++;
            }

        prodview = (GridView)findViewById(R.id.prodgridview);
        final CustomGridAdapter adapter = new CustomGridAdapter(ProductsActivity.this , iconss,arry);

        for(int i = 0 ; i < listcount ; i++)
        {
            if(id == i)
            {
                prodview.setAdapter(adapter);
                prodview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ProductsActivity.this, ItemsActivity.class);
                        intent.putExtra("name" ,arry.get(position).getName());
                        intent.putExtra("price" , arry.get(position).getPrice());
                        intent.putExtra("quant" , arry.get(position).getQuantity());
                        intent.putExtra("image" , iconss[position]);
                        intent.putExtra("categgid" , catid);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public void backbtn(View view) {
        Intent intent = new Intent(ProductsActivity.this , Home2Activity.class);
        startActivity(intent);
    }
}
