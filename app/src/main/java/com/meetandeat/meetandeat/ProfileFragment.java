package com.meetandeat.meetandeat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by Aaron on 13/03/2017.
 */

public class ProfileFragment extends Fragment {

    public ProfileFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View basicView = inflater.inflate(R.layout.fragment_profile, container, false);

        return basicView;
    }
}
