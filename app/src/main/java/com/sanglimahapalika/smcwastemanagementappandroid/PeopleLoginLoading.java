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

public class PeopleLoginLoading extends AppCompatActivity {

    DatabaseReference mRef;
    String mymobile,mypassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_login_loading);

        Intent intent = getIntent();
        mymobile=intent.getStringExtra("Mobile");
        mypassword=intent.getStringExtra("Password");



        mRef= FirebaseDatabase.getInstance().getReference().child("People").child(mymobile);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                try {
                    String mob=dataSnapshot.child("Mobile").getValue().toString();
                    String pass=dataSnapshot.child("Password").getValue().toString();


                    if (mymobile.equals(mob) && mypassword.equals(pass)){
                        User user=new User(PeopleLoginLoading.this);
                        user.setName(mymobile);
                        Intent intent=new Intent(PeopleLoginLoading.this,PeopleDashboard.class);
                        intent.putExtra("a",mymobile);
                        startActivity(intent);
                        finish();
                        Toast.makeText(PeopleLoginLoading.this, "Login Successful", Toast.LENGTH_LONG).show();


                    }
                    else{
                        Toast.makeText(PeopleLoginLoading.this, "Please Enter Valid Mobile or Password", Toast.LENGTH_LONG).show();
                        Intent intent1=new Intent(PeopleLoginLoading.this,PeopleLoginPage.class);
                        startActivity(intent1);
                        finish();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(PeopleLoginLoading.this, "Please Enter Valid Mobile And Password", Toast.LENGTH_LONG).show();
                    Intent intent2=new Intent(PeopleLoginLoading.this,PeopleLoginPage.class);
                    startActivity(intent2);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PeopleLoginLoading.this, "Check Mobile number or Password", Toast.LENGTH_LONG).show();
                Intent intent3=new Intent(PeopleLoginLoading.this,PeopleLoginPage.class);
                startActivity(intent3);
                finish();
            }
        });

    }
}
