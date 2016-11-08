package edu.orangecoastcollege.cs273.rbarron11.petprotector;

import android.net.Uri;

/**
 * Created by roman on 11/8/2016.
 */

public class Pet {
    private String mDetails;
    private int mId;
    private Uri mImageURI;
    private String mName;
    private String mPhone;

    public void Pet()
    {
        mId = -1;
        mName = "";
        mDetails = "";
        mPhone = "";
        mImageURI = Uri.parse("getUriToResource(this, R.drawable.none)");
    }
    public Pet(String mDetails, Uri mImageURI, String mName, String mPhone)
    {
        this(mDetails, -1, mImageURI, mName,  mPhone);
    }
    public Pet(String mDetails, int mId, Uri mImageURI, String mName, String mPhone) {
        this.mDetails = mDetails;
        this.mId = mId;
        this.mImageURI = mImageURI;
        this.mName = mName;
        this.mPhone = mPhone;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    public int getId() {
        return mId;
    }

    public Uri getImageURI() {
        return mImageURI;
    }

    public void setImageURI(Uri mImageURI) {
        this.mImageURI = mImageURI;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String toString()
    {
        String object = Integer.toString(mId)  + " "
                + mImageURI.toString() + " "
                + mName + " "
                + mDetails + " "
                + mPhone;
        return object;
    }

}
