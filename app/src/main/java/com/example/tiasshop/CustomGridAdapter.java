package com.example.tiasshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import java.util.ArrayList;

public class CustomGridAdapter extends BaseAdapter {

    private Integer icons[] ;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Products> arr;

    public CustomGridAdapter(Context context,Integer icon[],ArrayList<Products>arr)
    {
        this.context = context;
        this.icons = icon;
        this.arr = arr;
    }


    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return icons[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridview = convertView;
        if(gridview == null)
        {
            inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridview =inflater.inflate(R.layout.custom_layout ,null);
            ImageView icon = (ImageView)gridview.findViewById(R.id.iconimg);
            TextView pricetxt = (TextView)gridview.findViewById(R.id.price);
            TextView nametxt = (TextView)gridview.findViewById(R.id.name);
            TextView quanttxt = (TextView)gridview.findViewById(R.id.quantt);
            icon.setImageResource(R.drawable.cloth);
            nametxt.setText(arr.get(position).getName());
            pricetxt.setText(String.valueOf(arr.get(position).getPrice()));
            quanttxt.setText(String.valueOf(arr.get(position).getQuantity()));
        }

        return gridview;
    }
}
