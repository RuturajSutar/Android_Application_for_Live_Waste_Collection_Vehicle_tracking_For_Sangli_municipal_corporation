package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminAttendanceRegisterAbsent extends AppCompatActivity {

    String mydate,mymonth,myyear;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Attendance attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance_register_absent);

        Intent intent = getIntent();
        mydate=intent.getStringExtra("a");
        mymonth=intent.getStringExtra("b");
        myyear=intent.getStringExtra("c");


        attendance=new Attendance();

        listView=(ListView)findViewById(R.id.listView);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("Attendance").child(myyear).child(mymonth).child(mydate).child("Absent");

        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.attendance_info,R.id.attendanceinfo,list);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    attendance=ds.getValue(Attendance.class);
                    list.add(attendance.getName().toString());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
