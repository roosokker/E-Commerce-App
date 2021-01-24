package com.example.tiasshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener mdatesetlistener;
    EditText Fname , Lname , Email , Password , Job , Birthdate;
    Button createacc;
    RadioButton male , Female;
    String FN , LN , EM , PW , gender , bd , JOB;
    TiasDB cust;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        Fname = (EditText)findViewById(R.id.fn);
        Lname = (EditText)findViewById(R.id.ln);
        Email = (EditText)findViewById(R.id.em);
        Password = (EditText)findViewById(R.id.pw);
        male = (RadioButton)findViewById(R.id.maleradbtn);
        Female = (RadioButton)findViewById(R.id.femaleradbtn);
        Birthdate = (EditText)findViewById(R.id.bd);
        Job = (EditText)findViewById(R.id.job);
        createacc = (Button)findViewById(R.id.createaccbtn);
        cust = new TiasDB(this);
        loadingbar = new ProgressDialog(this);

        Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(male.isChecked()) {
                 male.setChecked(false);
                }
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Female.isChecked())
                    Female.setChecked(false);
            }
        });
        Birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog= new DatePickerDialog(
                        RegistrationActivity.this ,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth ,
                        mdatesetlistener ,
                        year , month , day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mdatesetlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month= month + 1;
                Log.d("TAG" , "onDateSet:mm/dd/yyy:" + month +"/" + dayOfMonth + "/" + year);
                String bd= month+"/"+dayOfMonth+"/"+year;
                Birthdate.setText(bd);
            }
        };


    }

    public void createacc(View view) {

        LN = Lname.getText().toString();
        EM = Email.getText().toString();
        FN = Fname.getText().toString();
        PW = Password.getText().toString();
        gender ="" ;
        if(male.isChecked())
        {

            gender = "Male";
        }
        else if (Female.isChecked())
        {
            gender = "Female";
        }

        bd = Birthdate.getText().toString();
        JOB = Job.getText().toString();
        if((FN.equals("") || LN.equals("") || EM.equals("") || PW.equals("") || gender.equals("") || bd.equals("") || JOB.equals("")))
        {
            if(FN.equals(""))
                Toast.makeText(this , "Please Enter First name" , Toast.LENGTH_LONG).show();
            if(LN.equals(""))
                Toast.makeText(this , "Please Enter Last Name" , Toast.LENGTH_LONG).show();

            if(EM.equals(""))

                Toast.makeText(this , "Please Enter your Email" , Toast.LENGTH_LONG).show();
            if(PW.equals(""))

                Toast.makeText(this , "Please Enter Password" , Toast.LENGTH_LONG).show();
            if(gender.equals(""))

                Toast.makeText(this , "Please Select Gender" , Toast.LENGTH_LONG).show();
            if(bd.equals(""))

                Toast.makeText(this , "Please Pick Your Birthdate" , Toast.LENGTH_LONG).show();
            if(JOB.equals(""))

                Toast.makeText(this , "Please Enter your Job" , Toast.LENGTH_LONG).show();
        }
        else
        {
            Boolean email = cust.getemail(EM);
            if(email == true)
            {
                Toast.makeText(this , "This Email is already in use" , Toast.LENGTH_LONG).show();
            }
            
            else
            {
                boolean insertchk = cust.insertCust(FN , LN , EM , PW , gender , bd , JOB);
                if(insertchk == false)
                {
                    Toast.makeText(this, "Can't Create Account Please Try Again", Toast.LENGTH_LONG).show();
                }
                else
                {
                    loadingbar.setTitle("Creating Account");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
            }

        }

    }
}
