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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class PeopleLoginPage extends AppCompatActivity {

    TextView registerPeople;
    TextView forgotPassword;
    Button loginButton;
    LinearLayout ll;
    Button btn1;
    EditText mobile,password;
    CheckBox showpassword;

    @Override
    public void onBackPressed() {
        Intent intent90 =new Intent(PeopleLoginPage.this,AllLoginButtons.class);
        startActivity(intent90);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_login_page);


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


        ll=(LinearLayout)findViewById(R.id.ll);
        btn1=(Button)findViewById(R.id.button1);
        loginButton=(Button)findViewById(R.id.button2);

        ll.setTranslationX(-1500);
        btn1.setTranslationX(1500);
        loginButton.setTranslationX(-1500);

        ll.animate().translationXBy(1500).setDuration(1000);
        btn1.animate().translationXBy(-1500).setDuration(1000);
        loginButton.animate().translationXBy(1500).setDuration(1000);

        registerPeople=(TextView)findViewById(R.id.textView3);
        registerPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PeopleLoginPage.this,PeopleRegistration.class);
                startActivity(intent);
                finish();

            }
        });

        forgotPassword=(TextView)findViewById(R.id.textView2);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PeopleLoginPage.this,PeopleFPPhoneNumber.class);
                startActivity(intent);
                finish();
            }
        });

        mobile=(EditText)findViewById(R.id.editText1);
        password=(EditText)findViewById(R.id.editText2);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mymobile=mobile.getText().toString();
                String mypassword=password.getText().toString();

                if (mymobile.isEmpty()){
                    mobile.setError("Please Enter Mobile Number");
                    mobile.requestFocus();
                    return;
                }
                else if (mymobile.length() != 10){
                    mobile.setError("Mobile number must contains 10 numbers");
                    mobile.requestFocus();
                    return;
                }
                else if (mypassword.isEmpty()){
                    password.setError("Please Enter Password");
                    password.requestFocus();
                    return;
                }
                else if (mymobile.isEmpty() && mypassword.isEmpty()){
                    mobile.setError("Please Enter Email");
                    password.setError("Please Enter Password");
                    mobile.requestFocus();
                    password.requestFocus();
                    return;
                }
                else if (!(mymobile.isEmpty() && mypassword.isEmpty())){

                    Intent intent=new Intent(PeopleLoginPage.this,PeopleLoginLoading.class);
                    intent.putExtra("Mobile",mymobile);
                    intent.putExtra("Password",mypassword);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(PeopleLoginPage.this, "Inalid Email or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
