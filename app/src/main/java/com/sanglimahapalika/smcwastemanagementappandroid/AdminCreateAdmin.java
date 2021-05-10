package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminCreateAdmin extends AppCompatActivity {

    Button createAdmin;
    CheckBox showpassword;
    EditText name,email,mobile,address,aadhar,password;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminCreateAdmin.this,AdminDashboard.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_admin);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        showpassword=(CheckBox)findViewById(R.id.showpassword);
        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });



        createAdmin=(Button)findViewById(R.id.button2);

        name=(EditText)findViewById(R.id.editText1);
        email=(EditText)findViewById(R.id.editText2);
        mobile=(EditText)findViewById(R.id.editText3);
        address=(EditText)findViewById(R.id.editText4);
        aadhar=(EditText)findViewById(R.id.editText5);
        password=(EditText)findViewById(R.id.editText6);

        createAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String myname=name.getText().toString();
                final String myemail=email.getText().toString();
                final String mymobile=mobile.getText().toString();
                final String myaddress=address.getText().toString();
                final String myaadhar=aadhar.getText().toString();
                final String mypassword=password.getText().toString();

                String removeDot=myemail.replace(".","");
                final String removeAt=removeDot.replace("@","");


                if(myname.isEmpty()){
                    name.setError("Enter Name");
                    name.requestFocus();
                    return;
                }
                else if (myemail.isEmpty()){
                    email.setError("Enter Email Address");
                    email.requestFocus();
                    return;
                }
                else if (mymobile.isEmpty()){
                    mobile.setError("Enter Mobile Number");
                    mobile.requestFocus();
                    return;
                }
                else if (mymobile.length() != 10){
                    mobile.setError("Mobile number must have 10 digits");
                    mobile.requestFocus();
                    return;
                }
                else if (myaddress.isEmpty()){
                    address.setError("Enter Your Address");
                    address.requestFocus();
                    return;
                }
                else if (myaadhar.isEmpty()){
                    aadhar.setError("Enter your aadhar Number");
                    aadhar.requestFocus();
                    return;
                }
                else if (mypassword.isEmpty()){
                    password.setError("Enter Your Password");
                    password.requestFocus();
                    return;
                }
                else if (mypassword.length()<6){
                    password.setError("Password must contains at least 6 characters");
                    password.requestFocus();
                    return;
                }
                else if (myname.isEmpty() && myemail.isEmpty() && mymobile.isEmpty() &&  myaddress.isEmpty() && myaadhar.isEmpty() && mypassword.isEmpty() ){
                    name.setError("Enter Name");
                    name.requestFocus();
                    return;
                }
                else if (!(myname.isEmpty() && myemail.isEmpty() && mymobile.isEmpty() &&  myaddress.isEmpty() && myaadhar.isEmpty() && mypassword.isEmpty())){


                    Intent intent = new Intent(AdminCreateAdmin.this,AdminCreateAdminLoading.class);
                    intent.putExtra("a",myname);
                    intent.putExtra("b",myemail);
                    intent.putExtra("c",mymobile);
                    intent.putExtra("d",myaddress);
                    intent.putExtra("e",myaadhar);
                    intent.putExtra("f",mypassword);
                    intent.putExtra("g",removeAt);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(AdminCreateAdmin.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
