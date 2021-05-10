package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminCreateAdminLoading extends AppCompatActivity {

    String myname,myemail,mymobile,myaddress,myaadhar,mypassword,removeAt;
    private DatabaseReference mRef;
    FirebaseAuth mFirebaseAuth;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminCreateAdminLoading.this);

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
        setContentView(R.layout.activity_admin_create_admin_loading);

        mFirebaseAuth=FirebaseAuth.getInstance();

        final Intent intent = getIntent();
        myname=intent.getStringExtra("a");
        myemail=intent.getStringExtra("b");
        mymobile=intent.getStringExtra("c");
        myaddress=intent.getStringExtra("d");
        myaadhar=intent.getStringExtra("e");
        mypassword=intent.getStringExtra("f");
        removeAt=intent.getStringExtra("g");


        mFirebaseAuth.createUserWithEmailAndPassword(myemail,mypassword).addOnCompleteListener(AdminCreateAdminLoading.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){


                    mRef= FirebaseDatabase.getInstance().getReference().child("Admin");
                    Map<String,Object> insertValues=new HashMap<>();
                    insertValues.put("Name",myname);
                    insertValues.put("Username",myemail);
                    insertValues.put("Mobile",mymobile);
                    insertValues.put("Address",myaddress);
                    insertValues.put("Aadhar",myaadhar);
                    insertValues.put("Password",mypassword);
                    mRef.child(removeAt).setValue(insertValues);
                    Intent intent = new Intent(AdminCreateAdminLoading.this,AdminDashboard.class);
                    startActivity(intent);
                    Toast.makeText(AdminCreateAdminLoading.this, "Admin Created Successfully", Toast.LENGTH_SHORT).show();
                    finish();


                }
                else {
                    Toast.makeText(AdminCreateAdminLoading.this, "Error in Creating Admin", Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(AdminCreateAdminLoading.this,AdminCreateAdmin.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });

    }
}
