package com.meetandeat.meetandeat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aaron on 05/04/2017.
 */

class customListView extends ArrayAdapter<listViewVariables>{

    private final ArrayList<listViewVariables> arrayListItems;

    public customListView(@NonNull Context context, ArrayList<listViewVariables> arrayListItems) {
        super(context, R.layout.custom_list_view, arrayListItems);

        this.arrayListItems = arrayListItems;
    }//find out how to do 2 different textViews



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewInflater = LayoutInflater.from(getContext());
        View customView = viewInflater.inflate(R.layout.custom_list_view, parent, false);

        //get the text views and the pictures from the view
        TextView nameDisplay = (TextView) customView.findViewById(R.id.nameDisplay);
        TextView restaurantDisplay = (TextView) customView.findViewById(R.id.restaurantDisplay);
        ImageView profilePictureDisplay = (ImageView) customView.findViewById(R.id.profilePictureDisplay);

        //set the text views and the picture views
        nameDisplay.setText(arrayListItems.get(position).getTestName());
        restaurantDisplay.setText(arrayListItems.get(position).getTestRest());
        profilePictureDisplay.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);

        return customView;
    }
}
