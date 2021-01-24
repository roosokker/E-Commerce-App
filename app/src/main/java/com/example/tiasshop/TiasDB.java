package com.example.tiasshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TiasDB extends SQLiteOpenHelper {
    private static String DBName = "TiaStore";
    private static int version = 14;
    SQLiteDatabase Tia;
    public TiasDB(Context context)
    {
        super(context, DBName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table Customer (custID integer primary key autoincrement , " +
                "custFname text  ,custLname text  ,PW text  ," +
                " gender text  , bd text , job text , email text );");

        db.execSQL("create table Categories (catID integer primary key autoincrement , " +
                "catname text );");

        db.execSQL("create table Orders(ordID integer primary key autoincrement , orddate text , " +
                "Address text , cust_id integer  , " +
                "foreign key (cust_id)references Customer (custID) );");

        db.execSQL("create table Products (prodID integer primary key autoincrement , " +
                "prodname text , price float , quantity integer, " +
                "cat_id integer, foreign key (cat_id) references Categories(catID));");

        db.execSQL("create table OrdersDetails (ord_id integer  , prod_id integer , quant integer , " +
                "constraint orddet_PK primary key (ord_id , prod_id), " +
                "constraint ordFK foreign key(ord_id) references Orders (ordID) , " +
                "constraint prodFK foreign key (prod_id) references Products (prodID));");

        db.execSQL("create table cart(cart_id integer primary key autoincrement , prodname text ," +
                "prodprice float , prodquant integer)");


        InsertCat(db);
        InsertProd(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists Customer");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists OrdersDetails");
        db.execSQL("drop table if exists cart");
        onCreate(db);
    }

    public boolean insertCust(String FN , String LN , String EM , String PW , String gender , String BD , String job)
    {
        Tia = getWritableDatabase();
        ContentValues customers = new ContentValues();
        customers.put("custFname" , FN);
        customers.put("custLname" , LN);
        customers.put("email" , EM);
        customers.put("PW" , PW);
        customers.put("gender" , gender);
        customers.put("bd" , BD);
        customers.put("job" , job);
        Long chk =Tia.insert("Customer" , null , customers);
        if(chk == -1)
            return false;
        else
            return true;
    }

    public boolean insertintocart(String name , float price , Integer quantity)
    {
        Tia = getWritableDatabase();
        ContentValues customers = new ContentValues();
        customers.put("prodname" , name);
        customers.put("prodprice" , price);
        customers.put("prodquant" , quantity);
        Long chk =Tia.insert("cart" , null , customers);
        if(chk == -1)
            return false;
        else
            return true;
    }

    public void InsertCat (SQLiteDatabase Db)
    {
        ContentValues cv1 = new ContentValues();
        cv1.put("catname" , "Women Clothes");
        Db.insert("Categories" , null , cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put("catname" , "Men Clothes");
        Db.insert("Categories" , null , cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put("catname" , "Mobiles");
        Db.insert("Categories" , null , cv3);

        ContentValues cv4 = new ContentValues();
        cv4.put("catname" , "Watches");
        Db.insert("Categories" , null , cv4);

        ContentValues cv5 = new ContentValues();
        cv5.put("catname" , "Bags");
        Db.insert("Categories" , null , cv5);

    }

    public void InsertProd(SQLiteDatabase Tia)
    {
        ContentValues cv1 = new ContentValues();
        cv1.put("prodname" , "Red T-shirt");
        cv1.put("price" , 100);
        cv1.put("quantity" , 5);
        cv1.put("cat_id" , 1);
        Tia.insert("Products" , null , cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put("prodname" , "Blouse");
        cv2.put("price" , 500);
        cv2.put("quantity" , 6);
        cv2.put("cat_id" , 1);
        Tia.insert("Products" , null , cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put("prodname" , "Pants");
        cv3.put("price" , 300);
        cv3.put("quantity" , 3);
        cv3.put("cat_id" , 1);
        Tia.insert("Products" , null , cv3);

        ContentValues cv4 = new ContentValues();
        cv4.put("prodname" , "Scarf");
        cv4.put("price" , 50);
        cv4.put("quantity" , 10);
        cv4.put("cat_id" , 1);
        Tia.insert("Products" , null , cv4);

        ContentValues cv5 = new ContentValues();
        cv5.put("prodname" , "Pyjama");
        cv5.put("price" , 600);
        cv5.put("quantity" , 8);
        cv5.put("cat_id" , 1);
        Tia.insert("Products" , null , cv5);
    }

    public boolean logincheck (String EM , String PW)
    {
        Tia = getReadableDatabase();
        String[] args = {EM , PW};
        Cursor cursor = Tia.rawQuery("Select email , PW from Customer where email = ? and PW = ? " , args);
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean getemail(String em)
    {
        Tia = getReadableDatabase();
        String[]arg={em};
        Cursor cursor = Tia.rawQuery("SELECT email FROM Customer WHERE email = ? ", arg);
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor getCategries()
    {
        Tia = getReadableDatabase();
        Cursor cursor = Tia.rawQuery("select catname from Categories" , null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
        }
        Tia.close();
        return cursor;
    }

    public Cursor getCategriesID(String catname)
    {
        Tia = getReadableDatabase();
        String[] arg = {catname};
        Cursor cursor = Tia.rawQuery("select catID from Categories where catname = ?" , arg);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
        }
        Tia.close();
        return cursor;
    }

    public Cursor getProduct(int catid)
    {
        Tia = getReadableDatabase();
        String[] args = {String.valueOf(catid)};
        Cursor cursor = Tia.rawQuery("select prodname,price,quantity from Products where cat_id = ?" , args);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
        }
        Tia.close();
        return cursor;
    }

    public Cursor getProductID(String name  , float price , Integer catid)
    {
        Tia = getReadableDatabase();
        String[] args = {name,String.valueOf(price),String.valueOf(catid)};
        Cursor cursor = Tia.rawQuery("select prodID from Products where prodname = ? and price = ? and cat_id = ?" , args);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
        }
        Tia.close();
        return cursor;
    }


    public Cursor getCartitems()
    {
        Tia = getReadableDatabase();
        Cursor cursor = Tia.rawQuery("select prodname , prodprice , prodquant from cart" , null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
        }
        Tia.close();
        return cursor;
    }

    public void updateprice(Integer price)
    {
        Tia = getWritableDatabase();
        ContentValues val1 = new ContentValues();
        val1.put("prodprice" , price);
        Tia.update("cart" ,val1 , null , null);
    }

    public void updatequant(Integer id , Integer quant)
    {
        Tia = getWritableDatabase();
        ContentValues val1 = new ContentValues();
        String[] arg = {String.valueOf(id)};
        val1.put("quantity" , quant);
        Tia.update("Products" ,val1 , "prodID = ? " , arg);
    }

    public void deleteitemfromcart( String name , float price , Integer quant)
    {
        Tia = getWritableDatabase();
        String p =String.valueOf(price);
        String q = String.valueOf(quant);
        String[] arg = {name , p , q};
        Tia.delete("cart" , "prodname = ? and prodprice = ? and prodquant = ? " , arg);
    }
}
