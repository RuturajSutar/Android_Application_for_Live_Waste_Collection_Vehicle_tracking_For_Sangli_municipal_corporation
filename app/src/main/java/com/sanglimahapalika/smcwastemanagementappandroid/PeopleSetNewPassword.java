package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PeopleSetNewPassword extends AppCompatActivity {

    Button changePassword;
    CheckBox showpassword;
    EditText newpassword;
    EditText newpasswordagain;
    DatabaseReference mRef;
    String mymobile;
    LottieAnimationView lottieAnimationView;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PeopleSetNewPassword.this);

        builder.setMessage("Are you sure to cancel this operation..?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(PeopleSetNewPassword.this,PeopleLoginPage.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
        setContentView(R.layout.activity_people_set_new_password);

        lottieAnimationView=(LottieAnimationView)findViewById(R.id.lottie);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        Intent intent=getIntent();
        mymobile=intent.getStringExtra("a");


        newpassword=(EditText)findViewById(R.id.editText1);
        newpasswordagain=(EditText)findViewById(R.id.editText2);

        showpassword=(CheckBox)findViewById(R.id.showpassword);
        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newpasswordagain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newpasswordagain.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        changePassword=(Button)findViewById(R.id.button2);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lottieAnimationView.setVisibility(View.VISIBLE);
                String newpass=newpassword.getText().toString();
                String newpassagain=newpasswordagain.getText().toString();

                if (newpass.isEmpty()){
                    newpassword.setError("Please Enter New Password");
                    newpassword.requestFocus();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    return;
                }
                else if (newpassagain.isEmpty()){
                    newpasswordagain.setError("Please Enter New Password Again");
                    newpasswordagain.requestFocus();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    return;
                }
                else if(!(newpass.equals(newpassagain))){
                    newpassword.setError("Both Passwords must be Same");
                    newpassword.requestFocus();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    return;
                }
                else if (newpass.equals(newpassagain)){

                    mRef= FirebaseDatabase.getInstance().getReference().child("People").child(mymobile);

                    Map<String,Object> updateValue=new HashMap<>();
                    updateValue.put("/Password",newpass);
                    mRef.updateChildren(updateValue);
                    Intent intent=new Intent(PeopleSetNewPassword.this,PeopleLoginPage.class);
                    Toast.makeText(PeopleSetNewPassword.this, "Password Changed Successfuly", Toast.LENGTH_SHORT).show();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(PeopleSetNewPassword.this, "Cannot change Password", Toast.LENGTH_SHORT).show();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                }


            }
        });
    }
}
