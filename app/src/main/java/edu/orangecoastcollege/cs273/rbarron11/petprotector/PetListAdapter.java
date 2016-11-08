package edu.orangecoastcollege.cs273.rbarron11.petprotector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to provide custom adapter for the <code>Task</code> list.
 */
public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context mContext;
    private List<Pet> mPetsList = new ArrayList<>();
    private int mResourceId;

    private LinearLayout listPetLinearLayout;
    private ImageView listPetImageView;
    private TextView listPetNameTextView;
    private TextView listPetDetailsTextView;
    private TextView listPetPhoneTextView;
    /**
     * Creates a new <code>TaskListAdapter</code> given a mContext, resource id and list of tasks.
     *
     * @param c The mContext for which the adapter is being used (typically an activity)
     * @param rId The resource id (typically the layout file name)
     * @param pets The list of tasks to display
     */
    public PetListAdapter(Context c, int rId, List<Pet> pets) {
        super(c, rId, pets);
        mContext = c;
        mResourceId = rId;
        mPetsList = pets;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);


        listPetImageView = (ImageView) view.findViewById(R.id.listItemImageView);
        listPetNameTextView = (TextView) view.findViewById(R.id.listItemNameTextView);
        listPetDetailsTextView = (TextView) view.findViewById(R.id.listItemDetailsTextView);
        listPetLinearLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);
        Pet selectedPet = mPetsList.get(pos);
        listPetLinearLayout.setTag(selectedPet);

        listPetNameTextView.setText(selectedPet.getName());
        listPetDetailsTextView.setText(selectedPet.getDetails());
        listPetImageView.setImageURI(selectedPet.getImageURI());
        return view;
    }
}
