package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PeopleDashboard extends AppCompatActivity {

    Button wasteSorting,addComplaint,trackDriver;

    String mymobile;


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PeopleDashboard.this);

        builder.setMessage("Are you sure logout..?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new User(PeopleDashboard.this).removeUser();
                        Intent intent = new Intent(PeopleDashboard.this,PeopleLoginPage.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_dashboard);


        Intent intent30 = getIntent();
        mymobile=intent30.getStringExtra("a");

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E26940"));
        actionBar.setBackgroundDrawable(colorDrawable);


        wasteSorting=(Button)findViewById(R.id.button1);
        addComplaint=(Button)findViewById(R.id.button2);
        trackDriver=(Button)findViewById(R.id.button3);

        wasteSorting.setTranslationX(-1500);
        addComplaint.setTranslationX(1500);
        trackDriver.setTranslationX(-1500);

        wasteSorting.animate().translationXBy(1500).setDuration(1500);
        addComplaint.animate().translationXBy(-1500).setDuration(1500);
        trackDriver.animate().translationXBy(1500).setDuration(1500);

        wasteSorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PeopleDashboard.this,PeopleWasteSorting.class);
                startActivity(intent);
            }
        });
        addComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PeopleDashboard.this,PeopleAddComplaint.class);
                intent.putExtra("a",mymobile);
                startActivity(intent);
            }
        });
        trackDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PeopleDashboard.this,PeopleTrackDrivers.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.custom_menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent=new Intent(PeopleDashboard.this,PeopleAccount.class);
                intent.putExtra("a",mymobile);
                startActivity(intent);
                return true;

            case R.id.item3:
                Intent intent3=new Intent(PeopleDashboard.this,PeopleChangePassword.class);
                intent3.putExtra("a",mymobile);
                startActivity(intent3);
                return true;
            case R.id.item2:

                AlertDialog.Builder builder = new AlertDialog.Builder(PeopleDashboard.this);

                builder.setMessage("Are you sure logout..?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new User(PeopleDashboard.this).removeUser();
                                Intent intent11=new Intent(PeopleDashboard.this,PeopleLoginPage.class);
                                startActivity(intent11);
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
