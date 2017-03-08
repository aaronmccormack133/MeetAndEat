package com.meetandeat.meetandeat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Aaron on 08/03/2017.
 */

public class ViewPageAdapter extends FragmentStatePagerAdapter{
    CharSequence Titles[];
    int NumbOfTabs;

    //constructor
    public ViewPageAdapter(FragmentManager fm, CharSequence mTitles[], int mNumOfTabs){
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumOfTabs;
    }

    //return the fragment for every position
    @Override
    public Fragment getItem(int position){
        if(position == 1){
            HomeTab hTab = new HomeTab();
            return hTab;
        }
        else if(position == 0){
            MessengerTab mTab = new MessengerTab();
            return mTab;
        }
        else if(position == 2){
            ProfileTab pTab = new ProfileTab();
            return pTab;
        }
        else{
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        return Titles[position];
    }

    @Override
    public int getCount(){
        return NumbOfTabs;
    }
}
