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

public class DriverAllComplaintsYear extends AppCompatActivity {

    String myyear;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_all_complaints_year);

        Intent intent = getIntent();
        myyear=intent.getStringExtra("a");

        Toast.makeText(this, myyear, Toast.LENGTH_SHORT).show();

        listView=(ListView)findViewById(R.id.listView);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("Complaints").child(myyear);


        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.a_r_y_info,R.id.aryinfo,list);


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    String months = ds.getKey().toString();
                    list.add(months);
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
                String mymonth = (String)listView.getItemAtPosition(position);
                Intent intent = new Intent(DriverAllComplaintsYear.this,DriverAllComplaintDate.class);
                intent.putExtra("a",mymonth);
                intent.putExtra("b",myyear);
                startActivity(intent);
            }
        });
    }
}
