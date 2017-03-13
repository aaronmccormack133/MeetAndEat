package com.meetandeat.meetandeat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Aaron on 13/03/2017.
 */

public class ProfileFragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";
    private TextView textView;

    public ProfileFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        textView = (TextView) rootView.findViewById(R.id.fragment_bottom_bar_text_activetab);

        String title = getArguments().getString(ARG_TITLE,"");
        textView.setText(title);

        return rootView;
    }
}
