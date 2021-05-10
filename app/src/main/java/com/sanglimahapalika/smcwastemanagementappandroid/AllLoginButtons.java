package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AllLoginButtons extends AppCompatActivity {

    Button adminButton,driverButton,peopleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_login_buttons);

        peopleButton=(Button)findViewById(R.id.button1);
        adminButton=(Button)findViewById(R.id.button2);
        driverButton=(Button)findViewById(R.id.button3);


//        adminButton.setTranslationY(2000);
//        driverButton.setTranslationY(2000);
//        peopleButton.setTranslationY(2000);
//
//        adminButton.animate().translationYBy(-2000).setDuration(2000);
//        driverButton.animate().translationYBy(-2000).setDuration(2000);
//        peopleButton.animate().translationYBy(-2000).setDuration(2000);
        peopleButton.setTranslationX(1500);
        adminButton.setTranslationX(2000);
        driverButton.setTranslationX(2500);

        peopleButton.animate().translationXBy(-1500).setDuration(1500);
        adminButton.animate().translationXBy(-2000).setDuration(2000);
        driverButton.animate().translationXBy(-2500).setDuration(2500);


        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllLoginButtons.this,AdminLoginPage.class);
                startActivity(intent);
            }
        });
        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllLoginButtons.this,DriverLoginPage.class);
                startActivity(intent);
            }
        });
        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllLoginButtons.this,PeopleLoginPage.class);
                startActivity(intent);
            }
        });
    }
}
