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

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AdminChangePassword extends AppCompatActivity {

    EditText oldPassword;
    Button changePassword;
    CheckBox showpassword;
    EditText newpassword;
    EditText newpasswordagain;
    DatabaseReference mRef1;
    DatabaseReference mRef;
    LottieAnimationView lottieAnimationView;
    String removeAt;
    public String opass,oemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_password);

        oldPassword=(EditText)findViewById(R.id.editText3);

        Intent intent30 = getIntent();
        removeAt=intent30.getStringExtra("a");

        lottieAnimationView=(LottieAnimationView)findViewById(R.id.lottie);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        newpassword=(EditText)findViewById(R.id.editText1);
        newpasswordagain=(EditText)findViewById(R.id.editText2);

        showpassword=(CheckBox)findViewById(R.id.showpassword);
        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    oldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newpasswordagain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    oldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
                final String oldpass=oldPassword.getText().toString();
                final String newpass=newpassword.getText().toString();
                String newpassagain=newpasswordagain.getText().toString();

                if (oldpass.isEmpty()){
                    oldPassword.setError("Please Enter Old Password");
                    oldPassword.requestFocus();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    return;
                }
                else if (newpass.isEmpty()){
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

                    mRef= FirebaseDatabase.getInstance().getReference().child("Admin").child(removeAt);
                    mRef1= FirebaseDatabase.getInstance().getReference().child("Admin").child(removeAt);
                    mRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            opass=dataSnapshot.child("Password").getValue().toString();
                            oemail=dataSnapshot.child("Username").getValue().toString();

                            if (oldpass.equals(opass)){


                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                if (user!=null){
                                    AuthCredential credential = EmailAuthProvider.getCredential(oemail,opass);

                                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            user.updatePassword(newpass)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Map<String,Object> updateValue=new HashMap<>();
                                                                updateValue.put("/Password",newpass);
                                                                mRef.updateChildren(updateValue);
                                                                Intent intent=new Intent(AdminChangePassword.this,AdminDashboard.class);
                                                                lottieAnimationView.setVisibility(View.INVISIBLE);
                                                                intent.putExtra("a",removeAt);
                                                                startActivity(intent);
                                                                finish();
                                                                Toast.makeText(AdminChangePassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(AdminChangePassword.this, "Cannot change Password", Toast.LENGTH_SHORT).show();
                                    lottieAnimationView.setVisibility(View.INVISIBLE);
                                }
                            }
                            else {
                                oldPassword.setError("Please Enter Correct Old Password");
                                oldPassword.requestFocus();
                                lottieAnimationView.setVisibility(View.INVISIBLE);
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(AdminChangePassword.this, "Error in Changing Password", Toast.LENGTH_SHORT).show();
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                        }
                    });


                }
                else {
                    Toast.makeText(AdminChangePassword.this, "Cannot change Password", Toast.LENGTH_SHORT).show();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                }


            }
        });
    }
}
