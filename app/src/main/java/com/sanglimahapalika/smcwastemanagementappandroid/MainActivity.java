package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView logo,swachh;
    private TextView name;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo=(ImageView)findViewById(R.id.logoImage);
        name=(TextView)findViewById(R.id.name);
        swachh=(ImageView)findViewById(R.id.imageView);

        logo.animate().alpha(1).setDuration(3000);
        name.animate().alpha(1).setDuration(3000);
        swachh.animate().alpha(1).setDuration(3000);

        final User user=new User(MainActivity.this);
        final Driver driver = new Driver(MainActivity.this);

        final Admin admin = new Admin(MainActivity.this);

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (user.getName() != ""){
                    Intent intent=new Intent(MainActivity.this,PeopleDashboard.class);
                    intent.putExtra("a",user.getName());
                    startActivity(intent);
                    finish();
                }
                else if (driver.getDname() != ""){
                    Intent intent10 = new Intent(MainActivity.this,DriverDashboard.class);
                    intent10.putExtra("a",driver.getDname());
                    startActivity(intent10);
                    finish();
                }
                else if (admin.getAname() != ""){
                    Intent intent20 = new Intent(MainActivity.this,AdminDashboard.class);
                    intent20.putExtra("a",admin.getAname());
                    startActivity(intent20);
                    finish();
                }
                else {
                    Intent intent=new Intent(MainActivity.this,AllLoginButtons.class);
                    startActivity(intent);
                    finish();
                }


            }
        },4000);


    }
}
