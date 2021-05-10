package com.sanglimahapalika.smcwastemanagementappandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PeopleAddComplaint extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    private Button camera;
    private Button gallery;
    String currentPhotoPath;
    StorageReference storageReference;
    private ProgressDialog mProgress;
    private EditText address;
    String mymobile;
    int year,month,day;
    Calendar c;
    DatabaseReference mRef,mRef1;


    public static final int REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_add_complaint);

        Intent intent = getIntent();
        mymobile=intent.getStringExtra("a");

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        address=(EditText)findViewById(R.id.editText);


        camera=(Button)findViewById(R.id.button);
        gallery=(Button)findViewById(R.id.button1);

        storageReference = FirebaseStorage.getInstance().getReference();
        mProgress=new ProgressDialog(this);







        if (ActivityCompat.checkSelfPermission(PeopleAddComplaint.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PeopleAddComplaint.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        else {
            Toast.makeText(this, "Location is assiccible", Toast.LENGTH_SHORT).show();
        }










        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myaddress=address.getText().toString();

                if (myaddress.isEmpty()){
                    address.setError("Please Enter Address or Discription");
                }
                else if (!(myaddress.isEmpty())){
                    askCameraPermissions();
                }
                else {
                    Toast.makeText(PeopleAddComplaint.this, "Error in Adding Complaint", Toast.LENGTH_SHORT).show();
                }
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myaddress=address.getText().toString();
                if (myaddress.isEmpty()){
                    address.setError("Please Enter Address or Discription");
                }
                else if (!(myaddress.isEmpty())){
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                }
                else {
                    Toast.makeText(PeopleAddComplaint.this, "Error in Adding Complaint", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }


    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                &&  ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERM_CODE);


        }
        else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();

            }
            else {
                Toast.makeText(this, "Camera Permission is Required to use the Camera", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                //selectedImage.setImageURI(Uri.fromFile(f));
                Log.i("abc", "URL is : " + Uri.fromFile(f));
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                uploadImageToFirebase(f.getName(), contentUri);
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.i("abcd", "Gallery Image URL is:  " + imageFileName);

                uploadImageToFirebase(imageFileName, contentUri);

            }
        }
    }

    private void uploadImageToFirebase(final String name, Uri contentUri) {
        c=Calendar.getInstance();
        year= c.get(Calendar.YEAR);
        month=c.get(Calendar.MONTH);
        day=c.get(Calendar.DATE);

        final String myyear=Integer.toString(year);
        final String mymonth=Integer.toString(month);
        final String myday=Integer.toString(day);



        mProgress.setMessage("Uploading Image ....");
        mProgress.show();



        final StorageReference image = storageReference.child("Complaints").child(myyear).child(mymonth).child(myday).child(mymobile).child("Photo/"+ name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        Log.i("abcde","Uploaded URL is : "+uri.toString());
//                        Picasso.get().load(uri).into(selectedImage);


                        mRef1=FirebaseDatabase.getInstance().getReference().child("People").child(mymobile);
                        mRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    final String myname = dataSnapshot.child("Name").getValue().toString();
                                    final String myaddressadd = dataSnapshot.child("Address").getValue().toString();
                                    final String mymob = dataSnapshot.child("Mobile").getValue().toString();
                                    final String myaddress=address.getText().toString();





                                    fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(PeopleAddComplaint.this);
                                    Task<Location> task = fusedLocationProviderClient.getLastLocation();
                                    task.addOnSuccessListener(new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            if (location != null){
                                                currentLocation=location;
                                                double latitude = currentLocation.getLatitude();
                                                double longitude = currentLocation.getLongitude();
                                                String mylat = Double.toString(latitude);
                                                String mylang = Double.toString(longitude);
                                                mRef= FirebaseDatabase.getInstance().getReference().child("Complaints").child(myyear).child(mymonth).child(myday).child(mymobile);
                                                Map<String,Object> insertValues=new HashMap<>();
                                                insertValues.put("Name",myname);
                                                insertValues.put("Address",myaddressadd);
                                                insertValues.put("Mobile",mymob);
                                                insertValues.put("URI",uri.toString());
                                                insertValues.put("Discription",myaddress);
                                                insertValues.put("FileName",name);
                                                insertValues.put("Latitude",mylat);
                                                insertValues.put("Longitude",mylang);
                                                mRef.setValue(insertValues);
                                                Toast.makeText(PeopleAddComplaint.this, currentLocation.getLatitude()+"    "+currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });






                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(PeopleAddComplaint.this, "Uploading failed", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(PeopleAddComplaint.this, "Image Uploading Failed", Toast.LENGTH_SHORT).show();
                            }
                        });


                        Toast.makeText(PeopleAddComplaint.this, "Image is Uploaded", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgress.dismiss();
                Toast.makeText(PeopleAddComplaint.this, "Image Uploading is Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.sanglimahapalika.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}
