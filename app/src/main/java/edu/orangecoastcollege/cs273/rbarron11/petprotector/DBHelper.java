package edu.orangecoastcollege.cs273.rbarron11.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    static final String DATABASE_NAME = "PetProtector"; //Data bases can have multiple tables
    private static final String DATABASE_TABLE = "Pets"; // this specific table is called tasks
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_IMAGE_URI = "image";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_PHONE_NUMBER = "phone_number";


    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_IMAGE_URI + " TEXT, "
                +FIELD_NAME + " TEXT, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_PHONE_NUMBER + " TEXT" + ")";
        database.execSQL (table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //create a method to add a brand new task to the database
    public void addPet(Pet newPet)
    {
        //step 1) create reference
        SQLiteDatabase db = this.getWritableDatabase();

        //step 2) make a key value pair  for each data type you want to insert
        ContentValues values = new ContentValues();

        values.put(FIELD_IMAGE_URI, newPet.getImageURI().toString());
        values.put(FIELD_NAME, newPet.getName());
        values.put(FIELD_DESCRIPTION, newPet.getDetails());
        values.put(FIELD_PHONE_NUMBER, newPet.getPhone());

        // step 3) insert the values into our database
        db.insert(DATABASE_TABLE, null,values);

        //Step 4) always remember to close database after usage
        db.close();
    }

    //Create a method to get all the tasks in the database;
    public ArrayList<Pet> getAllPets()
    {
        //create referece
        SQLiteDatabase db = this.getReadableDatabase();

        //make new empty array list
        ArrayList<Pet> allPets = new ArrayList<>();

        //Query the database for all records (all rows) and all fields (all columns)
        //return type of a Query is a Cursor

        Cursor results = db.query(DATABASE_TABLE, new String[]
                {KEY_FIELD_ID, FIELD_IMAGE_URI, FIELD_NAME, FIELD_DESCRIPTION, FIELD_PHONE_NUMBER}, null, null, null, null, null, null);

        if(results.moveToFirst())
        {
            do{
                int id = results.getInt(0);
                String image = results.getString(1);
                Uri imageUri = Uri.parse(image);
                String name = results.getString(2);
                String details = results.getString(3);
                String phone = results.getString(4);
                Pet pet = new Pet(details,imageUri,name,phone);
                allPets.add(pet);
            }while(results.moveToNext());
        }

        return allPets;
    }
}
