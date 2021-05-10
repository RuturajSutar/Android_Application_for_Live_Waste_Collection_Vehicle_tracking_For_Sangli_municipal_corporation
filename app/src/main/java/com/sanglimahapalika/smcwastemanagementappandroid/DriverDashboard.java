package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DriverDashboard extends AppCompatActivity {

    Button allComplaints,markAttendance;

    String mymobile;



    private FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;

    @Override
    public void onBackPressed()        {


        AlertDialog.Builder builder = new AlertDialog.Builder(DriverDashboard.this);

        builder.setMessage("Are you sure logout..?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Driver(DriverDashboard.this).removeUser();
                        Intent intent=new Intent(DriverDashboard.this,DriverLoginPage.class);
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
        setContentView(R.layout.activity_driver_dashboard);


        Intent intent30 = getIntent();
        mymobile=intent30.getStringExtra("a");


        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#C372DA"));
        actionBar.setBackgroundDrawable(colorDrawable);

        allComplaints=(Button)findViewById(R.id.button1);
        markAttendance=(Button)findViewById(R.id.button2);

        allComplaints.setTranslationX(-1500);
        markAttendance.setTranslationX(1500);


        allComplaints.animate().translationXBy(1500).setDuration(1500);
        markAttendance.animate().translationXBy(-1500).setDuration(1500);


        if (Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                //Request Location Again
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else {
                //Request Location Permission
                fetchLastLocation();
//                startService();
            }
        }
        else{
            //Start the Location service
            fetchLastLocation();
//            startService();
        }






        allComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DriverDashboard.this,DriverAllComplaints.class);
                startActivity(intent);
            }
        });
        markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DriverDashboard.this,DriverMarkAttendance.class);
                intent.putExtra("a",mymobile);
                startActivity(intent);
            }
        });
    }

    private void fetchLastLocation() {

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation=location;
                    DatabaseReference mRef1;
                    mRef1=FirebaseDatabase.getInstance().getReference().child("DriverLocation").child(mymobile);
                    Map<String,Object> insertValues=new HashMap<>();
                    insertValues.put("Latitude",currentLocation.getLatitude());
                    insertValues.put("Longitude",currentLocation.getLongitude());
                    mRef1.setValue(insertValues);
                    startService();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.custom_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent=new Intent(DriverDashboard.this,DriverAccount.class);
                intent.putExtra("a",mymobile);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent2=new Intent(DriverDashboard.this,TodaysComplaintsForDriver.class);
                startActivity(intent2);
                return true;

            case R.id.item4:
                Intent intent3=new Intent(DriverDashboard.this,DriverChangePassword.class);
                intent3.putExtra("a",mymobile);
                startActivity(intent3);
                return true;
            case R.id.item3:

                AlertDialog.Builder builder = new AlertDialog.Builder(DriverDashboard.this);

                builder.setMessage("Are you sure logout..?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Driver(DriverDashboard.this).removeUser();
                                Intent intent11=new Intent(DriverDashboard.this,DriverLoginPage.class);
                                startActivity(intent11);
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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    void startService(){
        LocationBroadcastReciever locationBroadcastReciever=new LocationBroadcastReciever();
        IntentFilter intentFilter=new IntentFilter("ACT_LOC");
        registerReceiver(locationBroadcastReciever,intentFilter);
        Intent intent = new Intent(DriverDashboard.this,LocationService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startService();
                }
                else {
                    Toast.makeText(this, "Give me Permission", Toast.LENGTH_LONG).show();
                }
        }
    }

    public class LocationBroadcastReciever extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACT_LOC")) {

                double lat, longitude;


                lat = intent.getDoubleExtra("latitude", 0f);
                longitude = intent.getDoubleExtra("longitude", 0f);



                DatabaseReference mRef;
                mRef= FirebaseDatabase.getInstance().getReference().child("DriverLocation").child(mymobile);

                Map<String,Object> updateValue=new HashMap<>();
                updateValue.put("/Latitude",lat);
                updateValue.put("/Longitude",longitude);
                mRef.updateChildren(updateValue);


                Toast.makeText(context, "Latitude is: " + lat + " and Longitude is: " + longitude, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
