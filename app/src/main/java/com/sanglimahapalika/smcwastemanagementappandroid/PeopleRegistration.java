package com.sanglimahapalika.smcwastemanagementappandroid;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PeopleRegistration extends AppCompatActivity {

    TextView loginPeople;
    Button toOTPPage;
    CheckBox showpassword;
    EditText name,mobile,address,aadhar,password;
    private DatabaseReference mRef;

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(PeopleRegistration.this,PeopleLoginPage.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_registration);

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

        loginPeople=(TextView)findViewById(R.id.textView2);
        loginPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PeopleRegistration.this,PeopleLoginPage.class);
                startActivity(intent);
                finish();
            }
        });


        name=(EditText)findViewById(R.id.editText1);
        mobile=(EditText)findViewById(R.id.editText2);
        address=(EditText)findViewById(R.id.editText3);
        aadhar=(EditText)findViewById(R.id.editText4);
        password=(EditText)findViewById(R.id.editText5);

        toOTPPage=(Button)findViewById(R.id.button2);
        toOTPPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myname=name.getText().toString();
                String mymobile=mobile.getText().toString();
                String myaddress=address.getText().toString();
                String myaadhar=aadhar.getText().toString();
                String mypassword=password.getText().toString();


                if(myname.isEmpty()){
                    name.setError("Enter Name");
                    name.requestFocus();
                    return;
                }
                else if (mymobile.isEmpty()){
                    mobile.setError("Enter Mobile Number");
                    mobile.requestFocus();
                    return;
                }
                else if (mymobile.length() != 10){
                    mobile.setError("Mobile number must contains 10 numbers");
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
                else if (myname.isEmpty() && mymobile.isEmpty() &&  myaddress.isEmpty() && myaadhar.isEmpty() && mypassword.isEmpty() ){
                    name.setError("Enter Name");
                    name.requestFocus();
                    return;
                }
                else if (!(myname.isEmpty() && mymobile.isEmpty() &&  myaddress.isEmpty() && myaadhar.isEmpty() && mypassword.isEmpty())){


                    Intent intent=new Intent(PeopleRegistration.this,PeopleOTO.class);
                    intent.putExtra("a",myname);
                    intent.putExtra("b",mymobile);
                    intent.putExtra("c",myaddress);
                    intent.putExtra("d",myaadhar);
                    intent.putExtra("e",mypassword);
                    startActivity(intent);
                    finish();



//                    Intent intent=new Intent(PeopleRegistration.this,PeopleOTO.class);
//                    startActivity(intent);
//                    finish();
                }
                else{
                    Toast.makeText(PeopleRegistration.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
