package edu.orangecoastcollege.cs273.rbarron11.petprotector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {


    private ImageView petImageView;
    //this member variable stores uri to whatever image has been selected
    //Default: none.png (R.drawable.none)
    private Uri imageURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        //hook up pet image view to layout
        petImageView = (ImageView) findViewById(R.id.petImageView);

        //connects a full URI to any android resource (id, drawable, color, layout, ect)
        //imageURI = getUriToResource(this, R.drawable.none);

        //Set the imageUri of the ImageView in code
        petImageView.setImageURI(imageURI);
    }

    public void selectPetImage(View view)
    {
        //list of all the permissions we need to ask the user
        ArrayList<String> permList = new ArrayList<>();

        //start by seeing if we already have permission to the camera
        int cameraPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if(cameraPermission != PackageManager.PERMISSION_GRANTED)
            permList.add( Manifest.permission.CAMERA);

        int readPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int writePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(writePermission != PackageManager.PERMISSION_GRANTED)
            permList.add( Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //if the list has items (size > 0) we need to ask permission
        if(permList.size() > 0)
        {
            //Convert the ArrayList to an Array of String
            String[] perms = new String[permList.size()];

            //request permission from the user
            int requestCode = 0;
            ActivityCompat.requestPermissions(this, permList.toArray(perms), requestCode);

        }

        if(cameraPermission == PackageManager.PERMISSION_GRANTED
                && readPermission == PackageManager.PERMISSION_GRANTED
                && writePermission == PackageManager.PERMISSION_GRANTED)
        {
            //use intent to lauch the gallery and take pictures
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 0);
        }
        else
            Toast.makeText(this,
                    "Pet Protector requires camera and external storage permission",
                    Toast.LENGTH_LONG);

    }


}
