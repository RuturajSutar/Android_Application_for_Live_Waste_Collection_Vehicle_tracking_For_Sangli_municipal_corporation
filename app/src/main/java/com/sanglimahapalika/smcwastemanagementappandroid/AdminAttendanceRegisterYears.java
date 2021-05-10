package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AdminAttendanceRegisterYears extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance_register_years);


        listView=(ListView)findViewById(R.id.listView);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("Attendance");


        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.a_r_y_info,R.id.aryinfo,list);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    String years = ds.getKey().toString();
                    list.add(years);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String myyear = (String)listView.getItemAtPosition(position);
                Intent intent = new Intent(AdminAttendanceRegisterYears.this,AdminAttendanceRegisterMonths.class);
                intent.putExtra("a",myyear);
                startActivity(intent);
            }
        });
    }
}
