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

public class AdminDashboard extends AppCompatActivity {

    Button createAdmin,createDriver,trackDriver,allComplaint;
    String removeAt,myemail;

    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboard.this);

        builder.setMessage("Are you sure logout..?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new Admin(AdminDashboard.this).removeUser();
                        Intent intent12 = new Intent(AdminDashboard.this,AdminLoginPage.class);
                        startActivity(intent12);
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
        setContentView(R.layout.activity_admin_dashboard);

        Intent intent30 = getIntent();
        myemail=intent30.getStringExtra("a");

        String removeDot=myemail.replace(".","");
        removeAt=removeDot.replace("@","");


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#F09F2C"));
        actionBar.setBackgroundDrawable(colorDrawable);


        createAdmin=(Button)findViewById(R.id.button1);
        createDriver=(Button)findViewById(R.id.button2);
        trackDriver=(Button)findViewById(R.id.button3);
        allComplaint=(Button)findViewById(R.id.button4);

        createAdmin.setTranslationX(-1500);
        createDriver.setTranslationX(1500);
        trackDriver.setTranslationX(-1500);
        allComplaint.setTranslationX(1500);

        createAdmin.animate().translationXBy(1500).setDuration(1500);
        createDriver.animate().translationXBy(-1500).setDuration(2000);
        trackDriver.animate().translationXBy(1500).setDuration(2500);
        allComplaint.animate().translationXBy(-1500).setDuration(3000);



        createAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,AdminCreateAdmin.class);
                startActivity(intent);
                finish();
            }
        });
        createDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,AdminCreateDriver.class);
                startActivity(intent);
                finish();
            }
        });
        trackDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,AdminTrackDrivers.class);
                startActivity(intent);
            }
        });
        allComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,AdminAllComplaints.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.cust_menu3,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent=new Intent(AdminDashboard.this,AdminAccount.class);
                intent.putExtra("a",removeAt);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent2=new Intent(AdminDashboard.this,TodaysComplaintsForAdmin.class);
                startActivity(intent2);
                return true;
            case R.id.item4:
                Intent intent3=new Intent(AdminDashboard.this,AdminChangePassword.class);
                intent3.putExtra("a",removeAt);
                startActivity(intent3);
                return true;
            case R.id.item3:

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboard.this);

                builder.setMessage("Are you sure logout..?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                new Admin(AdminDashboard.this).removeUser();
                                Intent intent19 = new Intent(AdminDashboard.this,AdminLoginPage.class);
                                startActivity(intent19);
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

            case R.id.item5:
                Intent intent5=new Intent(AdminDashboard.this,AdminTodaysPresent.class);
                intent5.putExtra("a",removeAt);
                startActivity(intent5);
                return true;

            case R.id.item6:
                Intent intent6=new Intent(AdminDashboard.this,AdminTodaysAbsent.class);
                intent6.putExtra("a",removeAt);
                startActivity(intent6);
                return true;

            case R.id.item7:
                Intent intent7=new Intent(AdminDashboard.this,AdminAttendanceRegisterYears.class);
//                intent7.putExtra("a",removeAt);
                startActivity(intent7);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
