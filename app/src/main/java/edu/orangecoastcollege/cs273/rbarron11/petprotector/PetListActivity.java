package edu.orangecoastcollege.cs273.rbarron11.petprotector;

import android.Manifest;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText detailsEditText;
    private EditText phoneEditText;

    private DBHelper database;
    private List<Pet> petList;
    private PetListAdapter petListAdapter;

    private ListView petListView;


    private ImageView petImageView;

    private Uri imageURI;
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        //hook up pet image view to layout
        petImageView = (ImageView) findViewById(R.id.petImageView);

        //connects a full URI to any android resource (id, drawable, color, layout, ect)
        imageURI = getUriResource(this, R.drawable.none);

        //Set the imageUri of the ImageView in code
        petImageView.setImageURI(imageURI);


        this.deleteDatabase(DBHelper.DATABASE_NAME);
        database = new DBHelper(this);
        petList = database.getAllPets();

        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item_layout, petList);
        petListView = (ListView) findViewById(R.id.petListView);
        petListView.setAdapter(petListAdapter);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        detailsEditText = (EditText) findViewById(R.id.detailsEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);

    }

    public void viewPetDetails(View view)
    {
        Intent petDetailsIntent = new Intent(this, PetDetailsActivity.class);
        Pet pet  = (Pet) view.getTag();
        Uri imageUri = pet.getImageURI();
        String petName = pet.getName();
        String petDetails = pet.getDetails();
        String petPhone = pet.getPhone();

        petDetailsIntent.putExtra("Name", petName);
        petDetailsIntent.putExtra("Details", petDetails);
        petDetailsIntent.putExtra("Phone", petPhone);
        petDetailsIntent.putExtra("Image", String.valueOf(imageUri));

        startActivity(petDetailsIntent);

    }

    public void addPet(View view)
    {
        String name = nameEditText.getText().toString();
        String details = detailsEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if(name.isEmpty())
        {
            Toast.makeText(this, "Pet name cannot be empty.",
                    Toast.LENGTH_SHORT).show();
        }
        else if (details.isEmpty())
        {
            Toast.makeText(this, "Pet description cannot be empty.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(phone.isEmpty())
        {
            Toast.makeText(this, "Phone number cannot be empty.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Pet pet = new Pet(details, imageURI, name, phone);
            petListAdapter.add(pet);
            database.addPet(pet);

            nameEditText.setText("");
            detailsEditText.setText("");
            phoneEditText.setText("");
            imageURI = getUriResource(this, R.drawable.none);
            petImageView.setImageURI(imageURI);
        }
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
            ActivityCompat.requestPermissions(this, permList.toArray(perms), REQUEST_CODE);

        }

        if(cameraPermission == PackageManager.PERMISSION_GRANTED
                && readPermission == PackageManager.PERMISSION_GRANTED
                && writePermission == PackageManager.PERMISSION_GRANTED)
        {
            //use intent to lauch the gallery and take pictures
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_CODE);
        }
        else
            Toast.makeText(this,
                    "Pet Protector requires camera and external storage permission",
                    Toast.LENGTH_LONG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //code to handle when the user closes the image gallery by selecting an image or pushing
        // the back button

        //the intent data is the URI selected from image gallery
        //decide if user selected an image
        if(data != null && requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            //set the imageUri to the data
            imageURI = data.getData();
            petImageView.setImageURI(imageURI);
        }
    }

    /**
     * Get URI to any resource type within  an android studio project static
     * to allow other classes to use  it as a helper function
     * @param context The current context
     * @param resId The resource by given id
     * @return Uri to resource by given id
     * @throws Resources.NotFoundException if the given resource does not exist
     */

    public static Uri getUriResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException
    {
        //Return  a resource instance for your application package
        Resources res = context.getResources();

        //return uri
        return  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" + res.getResourcePackageName(resId) + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));

    }

}
