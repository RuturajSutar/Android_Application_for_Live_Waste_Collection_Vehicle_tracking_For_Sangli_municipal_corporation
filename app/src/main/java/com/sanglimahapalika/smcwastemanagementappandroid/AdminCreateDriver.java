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
import android.widget.Toast;

public class AdminCreateDriver extends AppCompatActivity {


    Button registerDriver;
    CheckBox showpassword;
    EditText name,mobile,address,aadhar,password;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminCreateDriver.this,AdminDashboard.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_driver);

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


        name=(EditText)findViewById(R.id.editText1);
        mobile=(EditText)findViewById(R.id.editText2);
        address=(EditText)findViewById(R.id.editText3);
        aadhar=(EditText)findViewById(R.id.editText4);
        password=(EditText)findViewById(R.id.editText5);



        registerDriver=(Button)findViewById(R.id.button2);
        registerDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String myname=name.getText().toString();
                final String mymobile=mobile.getText().toString();
                final String myaddress=address.getText().toString();
                final String myaadhar=aadhar.getText().toString();
                final String mypassword=password.getText().toString();




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
                else if (myname.isEmpty() && mymobile.isEmpty() &&  myaddress.isEmpty() && myaadhar.isEmpty() && mypassword.isEmpty() ){
                    name.setError("Enter Name");
                    name.requestFocus();
                    return;
                }
                else if (!(myname.isEmpty() && mymobile.isEmpty() &&  myaddress.isEmpty() && myaadhar.isEmpty() && mypassword.isEmpty())){


                    Intent intent=new Intent(AdminCreateDriver.this,DriverRegisterOTP.class);
                    intent.putExtra("a",myname);
                    intent.putExtra("b",mymobile);
                    intent.putExtra("c",myaddress);
                    intent.putExtra("d",myaadhar);
                    intent.putExtra("e",mypassword);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(AdminCreateDriver.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
