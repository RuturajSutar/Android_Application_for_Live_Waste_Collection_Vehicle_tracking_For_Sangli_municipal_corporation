package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminSetNewPassword extends AppCompatActivity {

    Button changePassword;
    CheckBox showpassword;
    EditText newpassword;
    EditText newpasswordagain;
    DatabaseReference mRef;
    String removeAt,myemail,pass;

    FirebaseAuth firebaseAuth;
    LottieAnimationView lottieAnimationView;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminSetNewPassword.this);

        builder.setMessage("Are you sure to cancel this operation..?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent80 = new Intent(AdminSetNewPassword.this,AdminLoginPage.class);
                        startActivity(intent80);
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
        setContentView(R.layout.activity_admin_set_new_password);

        lottieAnimationView=(LottieAnimationView)findViewById(R.id.lottie);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        firebaseAuth=FirebaseAuth.getInstance();

        Intent intent=getIntent();
        removeAt=intent.getStringExtra("a");
        myemail=intent.getStringExtra("b");
        pass=intent.getStringExtra("c");


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

                final String newpass=newpassword.getText().toString();
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
                else if (newpass.length() < 6){
                    newpassword.setError("Password mus contains at least 6 characters");
                    newpassword.requestFocus();
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

                    firebaseAuth.signInWithEmailAndPassword(myemail,pass).addOnCompleteListener(AdminSetNewPassword.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                if (user!=null){
                                    AuthCredential credential = EmailAuthProvider.getCredential(myemail, pass);

                                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            user.updatePassword(newpass)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                mRef= FirebaseDatabase.getInstance().getReference().child("Admin").child(removeAt);

                                                                Map<String,Object> updateValue=new HashMap<>();
                                                                updateValue.put("/Password",newpass);
                                                                mRef.updateChildren(updateValue);
                                                                Intent intent=new Intent(AdminSetNewPassword.this,AdminLoginPage.class);
                                                                Toast.makeText(AdminSetNewPassword.this, "Password Changed Successfuly", Toast.LENGTH_SHORT).show();
                                                                startActivity(intent);
                                                                finish();
                                                                firebaseAuth.signOut();
                                                                lottieAnimationView.setVisibility(View.INVISIBLE);




                                                            }
                                                        }
                                                    });
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(AdminSetNewPassword.this, "Cannot change Password", Toast.LENGTH_SHORT).show();
                                    lottieAnimationView.setVisibility(View.INVISIBLE);
                                }
                            }
                            else {
                                Toast.makeText(AdminSetNewPassword.this, "Error", Toast.LENGTH_LONG).show();
                                Intent intent5=new Intent(AdminSetNewPassword.this,AdminLoginPage.class);
                                lottieAnimationView.setVisibility(View.INVISIBLE);
                                startActivity(intent5);
                                finish();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(AdminSetNewPassword.this, "Cannot change Password", Toast.LENGTH_SHORT).show();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
}
