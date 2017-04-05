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

/**
 * Created by Aaron on 05/04/2017.
 */

class customListView extends ArrayAdapter<String>{


    public customListView(@NonNull Context context, String[] testString, String[] testString2) {
        super(context, R.layout.custom_list_view, testString, testString2);
    }//find out how to do 2 different textViews



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewInflater = LayoutInflater.from(getContext());
        View customView = viewInflater.inflate(R.layout.custom_list_view, parent, false);

        String singleTestItem = getItem(position);
        TextView nameDisplay = (TextView) customView.findViewById(R.id.nameDisplay);
        TextView restaurantDisplay = (TextView) customView.findViewById(R.id.restaurantDisplay);
        ImageView profilePictureDisplay = (ImageView) customView.findViewById(R.id.profilePictureDisplay);

        nameDisplay.setText(singleTestItem);
        restaurantDisplay.setText(nextTestItem);
        profilePictureDisplay.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);

        return customView;
    }
}
