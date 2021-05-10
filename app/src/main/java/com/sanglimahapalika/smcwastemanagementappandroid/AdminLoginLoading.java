package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginLoading extends AppCompatActivity {


    DatabaseReference mRef;
    FirebaseAuth mFirebaseAuth;
    String myemail,mypassword,removeAt;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminLoginLoading.this);

        builder.setMessage("Wait while the operation is Done ....")
                .setCancelable(false)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_loading);

        final Intent intent = getIntent();
        myemail=intent.getStringExtra("Email");
        mypassword=intent.getStringExtra("Password");
        removeAt=intent.getStringExtra("Remove");



        mFirebaseAuth=FirebaseAuth.getInstance();

        mFirebaseAuth.signInWithEmailAndPassword(myemail,mypassword).addOnCompleteListener(AdminLoginLoading.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){



                    mRef= FirebaseDatabase.getInstance().getReference().child("Admin").child(removeAt);
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            try {
                                String mail=dataSnapshot.child("Username").getValue().toString();
                                String pass=dataSnapshot.child("Password").getValue().toString();


                                if (myemail.equals(mail) && mypassword.equals(pass)){
                                    Admin admin = new Admin(AdminLoginLoading.this);
                                    admin.setAname(myemail);
                                    Intent intent=new Intent(AdminLoginLoading.this,AdminDashboard.class);
                                    intent.putExtra("a",myemail);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(AdminLoginLoading.this, "Login Successful", Toast.LENGTH_LONG).show();


                                }
                                else{
                                    Toast.makeText(AdminLoginLoading.this, "Please Enter Valid Email or Password", Toast.LENGTH_LONG).show();
                                    Intent intent1=new Intent(AdminLoginLoading.this,AdminLoginPage.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(AdminLoginLoading.this, "Please Enter Valid Email Address Adn Password", Toast.LENGTH_LONG).show();
                                Intent intent2=new Intent(AdminLoginLoading.this,AdminLoginPage.class);
                                startActivity(intent2);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(AdminLoginLoading.this, "Check Email or Password", Toast.LENGTH_LONG).show();
                            Intent intent3=new Intent(AdminLoginLoading.this,AdminLoginPage.class);
                            startActivity(intent3);
                            finish();
                        }
                    });



                }
                else{
                    Toast.makeText(AdminLoginLoading.this, "Authentication Failed, Please Check Your Email and Password", Toast.LENGTH_LONG).show();
                    Intent intent5=new Intent(AdminLoginLoading.this,AdminLoginPage.class);
                    startActivity(intent5);
                    finish();
                }

            }
        });
    }
}
