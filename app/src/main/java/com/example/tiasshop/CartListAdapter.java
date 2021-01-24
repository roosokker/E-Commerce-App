package com.example.tiasshop;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class CartListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private Integer image[];
    private ArrayList<cartitems> cart;


    public CartListAdapter(Context context,ArrayList<cartitems> cart)
    {
        this.context = context;
        this.cart=cart;
    }

    @Override
    public int getCount() {
        return cart.size();
    }

    @Override
    public Object getItem(int position) {
        return cart;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    int n = 0;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridview = convertView;
        if(convertView == null)
        {
            inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridview = inflater.inflate(R.layout.cartcustomlayout ,null);
        }
        final ImageView icon = (ImageView)gridview.findViewById(R.id.imagee);
        final TextView pricetxt = (TextView)gridview.findViewById(R.id.Price);
        final TextView nametxt = (TextView)gridview.findViewById(R.id.Name);
        final TextView quanttxt = (TextView)gridview.findViewById(R.id.quantit);
        final ImageButton remvprod = (ImageButton) gridview.findViewById(R.id.removeprod);
        final ImageButton subquanti = (ImageButton)gridview.findViewById(R.id.subquant);
        final ImageButton addquanti = (ImageButton)gridview.findViewById(R.id.addquant);
        final LinearLayout l = (LinearLayout)gridview.findViewById(R.id.cartitemslayout);
        View layout = inflater.inflate(R.layout.activity_cart , null);
        final TiasDB tia = new TiasDB(context);
        final TextView totp = (TextView)layout.findViewById(R.id.totalprice);
        final String name = cart.get(position).getName();
        final float price = cart.get(position).getPrice();
        final Integer quant = cart.get(position).getQuantity();
        final String totalp = totp.getText().toString();

        final CartActivity ca = new CartActivity();

        remvprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float totalprice = Float.parseFloat(totalp);
                ca.deleteupdatequant();
                tia.deleteitemfromcart(name , price , quant);
                l.removeView(nametxt);
                l.removeView(pricetxt);
                l.removeView(quanttxt);
                l.removeView(icon);
                l.removeView(subquanti);
                l.removeView(addquanti);
                l.removeView(remvprod);
                totalprice -= price;
                totp.setText(String.valueOf(totalprice));
            }
        });


        addquanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ca.quantity !=0)
                {
                    n++;
                    quanttxt.setText(String.valueOf(n));
                    ca.quantity--;
                }

            }
        });

        icon.setImageResource(R.drawable.cloth);
        remvprod.setImageResource(R.drawable.ic_minus);
        pricetxt.setText(String.valueOf(cart.get(position).getPrice()));
        nametxt.setText(cart.get(position).getName());
        quanttxt.setText(String.valueOf(cart.get(position).getQuantity()));
        subquanti.setImageResource(R.drawable.ic_minus);
        addquanti.setImageResource(R.drawable.ic_add);
        return gridview;
    }
}
