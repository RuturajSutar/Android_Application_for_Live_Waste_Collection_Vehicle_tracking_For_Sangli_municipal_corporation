package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DriverMarkAttendance extends AppCompatActivity {

    CheckBox present,absent;
    Button attend;
    LottieAnimationView lottieAnimationView;
    int year,month,day;
    Calendar c;
    String mymobile;
    DatabaseReference mRef,mRef1,mRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mark_attendance);

        Intent intent = getIntent();
        mymobile=intent.getStringExtra("a");

        c=Calendar.getInstance();
        year= c.get(Calendar.YEAR);
        month=c.get(Calendar.MONTH);
        day=c.get(Calendar.DATE);

        present=(CheckBox)findViewById(R.id.checkBox1);
        absent=(CheckBox)findViewById(R.id.checkBox2);


        attend=(Button)findViewById(R.id.button2);

        lottieAnimationView=(LottieAnimationView)findViewById(R.id.lottie);

        final String myyear=Integer.toString(year);
        final String mymonth=Integer.toString(month);
        final String myday=Integer.toString(day);





        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                lottieAnimationView.setVisibility(View.VISIBLE);

                if (!(present.isChecked() || absent.isChecked())){
                    present.setError("Please Give Your Attendance First");
                    present.requestFocus();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    return;
                }
                else if (present.isChecked() && absent.isChecked()){
                    present.setError("Please Give Your Attendance in one Field");
                    present.requestFocus();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    return;
                }
                else if (present.isChecked()){
                    mRef1= FirebaseDatabase.getInstance().getReference().child("Attendance").child(myyear).child(mymonth).child(myday).child("Present");


                    mRef= FirebaseDatabase.getInstance().getReference().child("Driver").child(mymobile);
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("Name").getValue().toString();
                            Map<String,Object> insertValues=new HashMap<>();
                            insertValues.put("Name",name);
                            mRef1.child(mymobile).setValue(insertValues);
                            Intent intent = new Intent(DriverMarkAttendance.this,DriverDashboard.class);
                            intent.putExtra("a",mymobile);
                            startActivity(intent);
                            finish();
                            Toast.makeText(DriverMarkAttendance.this, "You Are Present Today", Toast.LENGTH_SHORT).show();
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(DriverMarkAttendance.this, "Error in Adding your Attendance", Toast.LENGTH_SHORT).show();
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                        }
                    });

                }
                else if (absent.isChecked()){
                    mRef2= FirebaseDatabase.getInstance().getReference().child("Attendance").child(myyear).child(mymonth).child(myday).child("Absent");


                    mRef= FirebaseDatabase.getInstance().getReference().child("Driver").child(mymobile);
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("Name").getValue().toString();
                            Map<String,Object> insertValues=new HashMap<>();
                            insertValues.put("Name",name);
                            mRef2.child(mymobile).setValue(insertValues);
                            Intent intent = new Intent(DriverMarkAttendance.this,DriverDashboard.class);
                            intent.putExtra("a",mymobile);
                            startActivity(intent);
                            finish();
                            Toast.makeText(DriverMarkAttendance.this, "You Are Absent Today", Toast.LENGTH_SHORT).show();
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(DriverMarkAttendance.this, "Error in Adding your Attendance", Toast.LENGTH_SHORT).show();
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                else {
                    Toast.makeText(DriverMarkAttendance.this, "Error in Adding your Attendance", Toast.LENGTH_SHORT).show();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                }
            }
        });


    }
}
