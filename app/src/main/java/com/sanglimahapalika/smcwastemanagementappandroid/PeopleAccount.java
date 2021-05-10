package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PeopleAccount extends AppCompatActivity {

    TextView one,name,three,mobilenumber,four,address,five,aadhar;
    DatabaseReference mRef;
    String mymobile;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_account);

        lottieAnimationView=(LottieAnimationView)findViewById(R.id.lottie);
        lottieAnimationView.setVisibility(View.VISIBLE);

        Intent intent10 = getIntent();
        mymobile=intent10.getStringExtra("a");

        one=(TextView)findViewById(R.id.textView1);
        name=(TextView)findViewById(R.id.textView2);
        three=(TextView)findViewById(R.id.textView5);
        mobilenumber=(TextView)findViewById(R.id.textView6);
        four=(TextView)findViewById(R.id.textView7);
        address=(TextView)findViewById(R.id.textView8);
        five=(TextView)findViewById(R.id.textView9);
        aadhar=(TextView)findViewById(R.id.textView10);

        mRef= FirebaseDatabase.getInstance().getReference().child("People").child(mymobile);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String myname=dataSnapshot.child("Name").getValue().toString();
                String mymobile=dataSnapshot.child("Mobile").getValue().toString();
                String myaddress=dataSnapshot.child("Address").getValue().toString();
                String myaadhar=dataSnapshot.child("Aadhar").getValue().toString();

                name.setText(myname);
                mobilenumber.setText(mymobile);
                address.setText(myaddress);
                aadhar.setText(myaadhar);

                one.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                three.setVisibility(View.VISIBLE);
                mobilenumber.setVisibility(View.VISIBLE);
                four.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                five.setVisibility(View.VISIBLE);
                aadhar.setVisibility(View.VISIBLE);

                lottieAnimationView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(PeopleAccount.this, "Error Occured", Toast.LENGTH_SHORT).show();
                lottieAnimationView.setVisibility(View.INVISIBLE);
            }
        });
    }
}
