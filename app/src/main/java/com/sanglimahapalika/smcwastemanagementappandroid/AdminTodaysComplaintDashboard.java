package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AdminTodaysComplaintDashboard extends AppCompatActivity {

    String mymobile,mydate,mymonth,myyear;
    DatabaseReference mRef;
    TextView thisname,one,thismobile,two,thisaddress,three,thisdiscription,four;
    ImageView selectedImage;
    Button viewLocation;
    private ProgressDialog mProgress;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_todays_complaint_dashboard);

        Intent intent = getIntent();
        mymobile=intent.getStringExtra("a");
        mydate=intent.getStringExtra("b");
        mymonth=intent.getStringExtra("c");
        myyear=intent.getStringExtra("d");
        Toast.makeText(this, mymobile+"    "+mydate+"-"+mymonth+"-"+myyear, Toast.LENGTH_SHORT).show();

        mProgress=new ProgressDialog(this);

        storageReference = FirebaseStorage.getInstance().getReference();

        thisname=(TextView)findViewById(R.id.textView1);
        one=(TextView)findViewById(R.id.textView2);
        thismobile=(TextView)findViewById(R.id.textView3);
        two=(TextView)findViewById(R.id.textView4);
        thisaddress=(TextView)findViewById(R.id.textView5);
        three=(TextView)findViewById(R.id.textView6);
        thisdiscription=(TextView)findViewById(R.id.textView7);
        four=(TextView)findViewById(R.id.textView8);

        selectedImage=(ImageView)findViewById(R.id.imageView);
        viewLocation=(Button)findViewById(R.id.button2);


        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Complaints").child(myyear).child(mymonth).child(mydate).child(mymobile);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            String mylat=dataSnapshot.child("Latitude").getValue().toString();
                            String mylang=dataSnapshot.child("Longitude").getValue().toString();

                            Intent intent1 = new Intent(AdminTodaysComplaintDashboard.this,ComplaintLocation.class);
                            intent1.putExtra("a",mylat);
                            intent1.putExtra("b",mylang);
                            startActivity(intent1);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(AdminTodaysComplaintDashboard.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        mRef= FirebaseDatabase.getInstance().getReference().child("Complaints").child(myyear).child(mymonth).child(mydate).child(mymobile);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mProgress.setMessage("Downloading Image ....");
                mProgress.show();

                String name = dataSnapshot.child("Name").getValue().toString();
                String mobile = dataSnapshot.child("Mobile").getValue().toString();
                String address = dataSnapshot.child("Address").getValue().toString();
                String discription = dataSnapshot.child("Discription").getValue().toString();
                String myuri = dataSnapshot.child("URI").getValue().toString();
                String filename = dataSnapshot.child("FileName").getValue().toString();

                one.setText(name);
                two.setText(mobile);
                three.setText(address);
                four.setText(discription);

                thisname.setVisibility(View.VISIBLE);
                one.setVisibility(View.VISIBLE);
                thismobile.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                thisaddress.setVisibility(View.VISIBLE);
                three.setVisibility(View.VISIBLE);
                thisdiscription.setVisibility(View.VISIBLE);
                four.setVisibility(View.VISIBLE);

                final StorageReference image = storageReference.child("Complaints").child(myyear).child(mymonth).child(mydate).child(mymobile).child("Photo/"+ filename);
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("abcde","Uploaded URL is : "+uri.toString());
                        Picasso.get().load(uri).into(selectedImage);
                        selectedImage.setVisibility(View.VISIBLE);
                        mProgress.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminTodaysComplaintDashboard.this, "Error in Loading Files", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
