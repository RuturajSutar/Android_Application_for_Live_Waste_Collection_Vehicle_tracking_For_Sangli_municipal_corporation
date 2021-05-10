package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AdminTodaysAbsent extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Attendance attendance;
    int year,month,day;
    Calendar c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_todays_absent);

        c=Calendar.getInstance();
        year= c.get(Calendar.YEAR);
        month=c.get(Calendar.MONTH);
        day=c.get(Calendar.DATE);

        final String myyear=Integer.toString(year);
        final String mymonth=Integer.toString(month);
        final String myday=Integer.toString(day);

        attendance=new Attendance();

        listView=(ListView)findViewById(R.id.listView);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("Attendance").child(myyear).child(mymonth).child(myday).child("Absent");

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
