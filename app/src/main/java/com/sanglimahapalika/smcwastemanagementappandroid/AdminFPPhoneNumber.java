package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminFPPhoneNumber extends AppCompatActivity {

    Button number;
    EditText email;
    DatabaseReference mRef;
    LottieAnimationView lottieAnimationView;


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AdminFPPhoneNumber.this,AdminLoginPage.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fpphone_number);


        lottieAnimationView=(LottieAnimationView)findViewById(R.id.lottie);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        email=(EditText)findViewById(R.id.editText);

        number=(Button)findViewById(R.id.button2);
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lottieAnimationView.setVisibility(View.VISIBLE);

                final String myemail=email.getText().toString();

                String removeDot=myemail.replace(".","");
                final String removeAt=removeDot.replace("@","");

                if (myemail.isEmpty()){
                    email.setError("Please Enter Your registered email address");
                    email.requestFocus();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    return;
                }

                else if (!(myemail.isEmpty())){



                    mRef= FirebaseDatabase.getInstance().getReference().child("Admin").child(removeAt);
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            try {
                                String mob=dataSnapshot.child("Mobile").getValue().toString();
                                String pass=dataSnapshot.child("Password").getValue().toString();
                                Toast.makeText(AdminFPPhoneNumber.this, mob, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(AdminFPPhoneNumber.this,AdminVerifyOTPFP.class);
                                intent.putExtra("a",mob);
                                intent.putExtra("b",removeAt);
                                intent.putExtra("c",myemail);
                                intent.putExtra("d",pass);
                                startActivity(intent);
                                lottieAnimationView.setVisibility(View.INVISIBLE);
                                finish();
                                Toast.makeText(AdminFPPhoneNumber.this, "Correct Email Address", Toast.LENGTH_LONG).show();

                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(AdminFPPhoneNumber.this, "Please Enter Registered Email Address", Toast.LENGTH_LONG).show();
                                lottieAnimationView.setVisibility(View.INVISIBLE);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(AdminFPPhoneNumber.this, "Please Enter Registered Email Address", Toast.LENGTH_LONG).show();
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                        }
                    });



                }
                else {
                    Toast.makeText(AdminFPPhoneNumber.this, "Please Enter Registered Email Address", Toast.LENGTH_SHORT).show();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
}
