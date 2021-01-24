package com.example.tiasshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static String Pref_Name = "prefFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getprefdata();
    }

    private void getprefdata() {
        SharedPreferences SP = getSharedPreferences(Pref_Name, MODE_PRIVATE);
        if (SP.contains("prefsemail") && SP.contains("prefspw") && SP.contains("prefscheck"))
        {
            Intent intent = new Intent(MainActivity.this, Home2Activity.class);
            startActivity(intent);
        }

    }
    public void loginbtn(View view) {
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
    }

    public void registerbtn(View view) {
        Intent intent = new Intent(this , RegistrationActivity.class);
        startActivity(intent);
    }
}
