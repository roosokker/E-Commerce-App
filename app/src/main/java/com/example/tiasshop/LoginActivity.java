package com.example.tiasshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText Password;
    Button login;
    Button register;
    TiasDB shopDB;
    CheckBox rememberme;
    SharedPreferences mprefs;
    private static String Pref_Name = "prefFile";
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mprefs = getSharedPreferences(Pref_Name , MODE_PRIVATE);
        email = (EditText)findViewById(R.id.emailtxt);
        Password = (EditText)findViewById(R.id.pwtxt);
        login = (Button)findViewById(R.id.loginbtn);
        shopDB = new TiasDB(this);
        rememberme = (CheckBox)findViewById(R.id.rememberme);
        loadingbar = new ProgressDialog(this);
        getprefdata();
    }

    private void getprefdata() {
        SharedPreferences SP = getSharedPreferences(Pref_Name , MODE_PRIVATE);
        if(SP.contains("prefsemail"))
        {
            String setemail = SP.getString("prefsemail" , "Not Found");
            email.setText(setemail.toString());
        }
        if(SP.contains("prefspw"))
        {
            String setpw = SP.getString("prefspw" , "Not Found");
            Password.setText(setpw.toString());
        }
        if(SP.contains("prefscheck"))
        {
            Boolean setcheck = SP.getBoolean("prefscheck" , false);
            rememberme.setChecked(setcheck);
        }
    }


    public void loginclick(View view) {

        String em = email.getText().toString();
        String pw = Password.getText().toString();
        if (email.equals("") )
        {
            Toast.makeText(this , "Please enter your email" , Toast.LENGTH_SHORT).show();
        }
        if(pw.equals(""))
        {
            Toast.makeText(this,"Please enter your password" , Toast.LENGTH_SHORT).show();
        }
        else {
            boolean check = shopDB.logincheck(em, pw);
            if (check == false) {
                Toast.makeText(this, "User Not Found", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Incorrect Email or Password please login again with the correct one", Toast.LENGTH_LONG).show();
            } else {
                if (rememberme.isChecked()) {
                    boolean ischecked = rememberme.isChecked();
                    SharedPreferences.Editor editor = mprefs.edit();
                    editor.putString("prefsemail", em);
                    editor.putString("prefspw", pw);
                    editor.putBoolean("prefscheck", ischecked);
                    editor.apply();
                } else
                    mprefs.edit().clear().apply();

                loadingbar.setTitle("Logging in...");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
                Intent intent = new Intent(this, Home2Activity.class);
                startActivity(intent);
                email.setText("");
                Password.setText("");
            }
        }

    }
}
