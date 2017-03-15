package com.meetandeat.meetandeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//http://blog.iamsuleiman.com/using-bottom-navigation-view-android-design-support-library/
//https://github.com/1priyank1/BottomNavigation-Demo
//

public class MainActivity extends AppCompatActivity {

    private TextView textProfiles;
    private TextView textHome;
    private TextView textMessenger;

    ViewPager myPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fragments
        myPager = (ViewPager) findViewById(R.id.Pager);
        myPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));


        //Navigation
        textHome = (TextView) findViewById(R.id.text_home);
        textProfiles = (TextView) findViewById(R.id.text_profiles);
        textMessenger = (TextView) findViewById(R.id.text_messenger);

//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//           findViewById(R.id.bottom_navigation);
/*
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Nullable
                    public boolean onNavigationItemSelected(@NonNull MenuItem item){
                        switch(item.getItemId()){
                            case R.id.action_messenger:
                                //textHome.setVisibility(View.GONE);
                                //textMessenger.setVisibility(View.VISIBLE);
                                //textProfiles.setVisibility(View.GONE);
                                break;
                            case R.id.action_restaurants:
                                //textHome.setVisibility(View.VISIBLE);
                                //textMessenger.setVisibility(View.GONE);
                                //textProfiles.setVisibility(View.GONE);
                                break;
                            case R.id.action_profiles:
                                //textHome.setVisibility(View.GONE);
                                //textMessenger.setVisibility(View.GONE);
                                //textProfiles.setVisibility(View.VISIBLE);
                                break;
                        }
                        return true;
                    }
                }
        );
*/
    }

    public class SamplePagerAdapter extends FragmentPagerAdapter{
        public SamplePagerAdapter(FragmentManager fragM){
            super(fragM);
        }
        @Override
        public Fragment getItem(int position){
            if(position == 0){
                return new MessageFragment();

            }
            else if(position == 1){
                return new HomeFragment();
            }
            else{
                return new ProfileFragment();
            }
        }
        @Override
        public int getCount(){
            return 3;
        }
    }

    public void onClick(View view){
        Intent i = new Intent(this, Restaurants.class);
        startActivity(i);
    }
}
