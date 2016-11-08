package edu.orangecoastcollege.cs273.rbarron11.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PetDetailsActivity extends AppCompatActivity {

    public ImageView petImageView;
    public TextView petNameTextView;
    public TextView petDetailsTextView;
    public TextView petPhoneTextView;

    private String petName;
    private String petDetails;
    private String petPhone;
    private String petImage;
    private Uri myUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        petNameTextView = (TextView) findViewById(R.id.petNameTextView);
        petDetailsTextView = (TextView) findViewById(R.id.petDetailsTextView);
        petPhoneTextView = (TextView) findViewById(R.id.petPhoneTextView);
        petImageView = (ImageView) findViewById(R.id.listPetImageView);

        Intent IntentFromList = getIntent();

        petName = IntentFromList.getStringExtra("Name");
        petDetails = IntentFromList.getStringExtra("Details");
        petPhone = IntentFromList.getStringExtra("Phone");
        petImage = IntentFromList.getStringExtra("Image");
        myUri = Uri.parse(petImage);

        petNameTextView.setText(petName);
        petDetailsTextView.setText(petDetails);
        petPhoneTextView.setText(petPhone);
        petImageView.setImageURI(myUri);
    }
}
