package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminAttendanceRegisterPresentAbsent extends AppCompatActivity {

    String mydate,mymonth,myyear;
    Button present,absent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance_register_present_absent);

        Intent intent = getIntent();
        mydate=intent.getStringExtra("a");
        mymonth=intent.getStringExtra("b");
        myyear=intent.getStringExtra("c");

        Toast.makeText(this, mydate+"-"+mymonth+"-"+myyear, Toast.LENGTH_SHORT).show();

        present=(Button)findViewById(R.id.button1);
        absent=(Button)findViewById(R.id.button2);

        present.setTranslationX(-1500);
        absent.setTranslationX(1500);


        present.animate().translationXBy(1500).setDuration(1500);
        absent.animate().translationXBy(-1500).setDuration(1500);


        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminAttendanceRegisterPresentAbsent.this,AdminAttendanceRegisterPresent.class);
                intent.putExtra("a",mydate);
                intent.putExtra("b",mymonth);
                intent.putExtra("c",myyear);
                startActivity(intent);
            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminAttendanceRegisterPresentAbsent.this,AdminAttendanceRegisterAbsent.class);
                intent.putExtra("a",mydate);
                intent.putExtra("b",mymonth);
                intent.putExtra("c",myyear);
                startActivity(intent);
            }
        });

    }
}
