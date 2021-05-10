package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class TodaysComplaintsForDriver extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    TodaysComplaintClass todaysComplaintClass;
    int year,month,day;
    Calendar c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_complaints_for_driver);

        c=Calendar.getInstance();
        year= c.get(Calendar.YEAR);
        month=c.get(Calendar.MONTH);
        day=c.get(Calendar.DATE);

        final String myyear=Integer.toString(year);
        final String mymonth=Integer.toString(month);
        final String myday=Integer.toString(day);

        todaysComplaintClass=new TodaysComplaintClass();

        listView=(ListView)findViewById(R.id.listView);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("Complaints").child(myyear).child(mymonth).child(myday);

        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.todays_complaint_info,R.id.todayscomplaintinfo,list);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    todaysComplaintClass=ds.getValue(TodaysComplaintClass.class);
                    list.add("Mobile : "+todaysComplaintClass.getMobile().toString()+"\n"+"Name : "+todaysComplaintClass.getName().toString()+"\n"+"Address : "+todaysComplaintClass.getAddress().toString()+"\n"+"Discription : "+todaysComplaintClass.getDiscription().toString());                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mob = list.get(position).substring(9,19);
                Intent intent = new Intent(TodaysComplaintsForDriver.this,DriverTodaysComplaintDashboard.class);
                intent.putExtra("a",mob);
                intent.putExtra("b",myday);
                intent.putExtra("c",mymonth);
                intent.putExtra("d",myyear);
                startActivity(intent);
            }
        });
    }
}
