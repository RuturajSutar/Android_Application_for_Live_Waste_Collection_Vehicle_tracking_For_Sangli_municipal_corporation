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
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class AdminLoginPage extends AppCompatActivity {


    TextView forgotPassword;
    Button loginButton;
    LinearLayout ll;
    Button btn1;
    EditText email,password;
    CheckBox showpassword;

    @Override
    public void onBackPressed() {
        Intent intent90 =new Intent(AdminLoginPage.this,AllLoginButtons.class);
        startActivity(intent90);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);



        email=(EditText)findViewById(R.id.editText1);
        password=(EditText)findViewById(R.id.editText2);

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

        forgotPassword=(TextView)findViewById(R.id.textView2);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminLoginPage.this,AdminFPPhoneNumber.class);
                startActivity(intent);
                finish();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String myemail=email.getText().toString();
                final String mypassword=password.getText().toString();


                String removeDot=myemail.replace(".","");
                final String removeAt=removeDot.replace("@","");



                if (myemail.isEmpty()){
                    email.setError("Please Enter Email");
                    email.requestFocus();
                    return;
                }
                else if (mypassword.isEmpty()){
                    password.setError("Please Enter Password");
                    password.requestFocus();
                    return;
                }
                else if (myemail.isEmpty() && mypassword.isEmpty()){
                    email.setError("Please Enter Email");
                    password.setError("Please Enter Password");
                    email.requestFocus();
                    password.requestFocus();
                    return;
                }
                else if (!(myemail.isEmpty() && mypassword.isEmpty())){

                    Intent intent=new Intent(AdminLoginPage.this,AdminLoginLoading.class);
                    intent.putExtra("Email",myemail);
                    intent.putExtra("Password",mypassword);
                    intent.putExtra("Remove",removeAt);
                    startActivity(intent);
                    finish();
                }
                else {

                    Toast.makeText(AdminLoginPage.this, "Inalid Email or Password", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
