package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverLoginLoading extends AppCompatActivity {

    DatabaseReference mRef;
    String mymobile,mypassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_loading);

        Intent intent = getIntent();
        mymobile=intent.getStringExtra("Mobile");
        mypassword=intent.getStringExtra("Password");


        mRef= FirebaseDatabase.getInstance().getReference().child("Driver").child(mymobile);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                try {
                    String mob=dataSnapshot.child("Mobile").getValue().toString();
                    String pass=dataSnapshot.child("Password").getValue().toString();


                    if (mymobile.equals(mob) && mypassword.equals(pass)){
                        Driver driver = new Driver(DriverLoginLoading.this);
                        driver.setDname(mymobile);
                        Intent intent=new Intent(DriverLoginLoading.this,DriverDashboard.class);
                        intent.putExtra("a",mymobile);
                        startActivity(intent);
                        finish();
                        Toast.makeText(DriverLoginLoading.this, "Login Successful", Toast.LENGTH_LONG).show();


                    }
                    else{
                        Toast.makeText(DriverLoginLoading.this, "Please Enter Valid Mobile or Password", Toast.LENGTH_LONG).show();
                        Intent intent1=new Intent(DriverLoginLoading.this,DriverLoginPage.class);
                        startActivity(intent1);
                        finish();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(DriverLoginLoading.this, "Please Enter Valid Mobile And Password", Toast.LENGTH_LONG).show();
                    Intent intent2=new Intent(DriverLoginLoading.this,DriverLoginPage.class);
                    startActivity(intent2);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DriverLoginLoading.this, "Check Mobile number or Password", Toast.LENGTH_LONG).show();
                Intent intent3=new Intent(DriverLoginLoading.this,DriverLoginPage.class);
                startActivity(intent3);
                finish();
            }
        });
    }
}
