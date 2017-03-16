package com.meetandeat.meetandeat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;



/**
 * Created by Aaron on 13/03/2017.
 */

public class HomeFragment extends Fragment {

    public HomeFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View basicView =  inflater.inflate(R.layout.fragment_home, container, false);
        return basicView;
    }
}
