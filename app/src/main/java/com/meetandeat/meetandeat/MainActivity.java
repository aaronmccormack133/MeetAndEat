package com.meetandeat.meetandeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

//http://blog.iamsuleiman.com/using-bottom-navigation-view-android-design-support-library/
//https://github.com/1priyank1/BottomNavigation-Demo

public class MainActivity extends AppCompatActivity {

    private TextView textProfiles;
    private TextView textHome;
    private TextView textMessenger;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fragments
        fragment = new MessageFragment();
        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_fragmentholder, fragment).commit();

        //Navigation
        textHome = (TextView) findViewById(R.id.text_home);
        textProfiles = (TextView) findViewById(R.id.text_profiles);
        textMessenger = (TextView) findViewById(R.id.text_messenger);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
            findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Nullable
                    public boolean onNavigationItemSelected(@NonNull MenuItem item){
                        switch(item.getItemId()){
                            case R.id.action_messenger:
                                textHome.setVisibility(View.GONE);
                                textMessenger.setVisibility(View.VISIBLE);
                                textProfiles.setVisibility(View.GONE);
                                fragment = new MessageFragment();
                                break;
                            case R.id.action_restaurants:
                                textHome.setVisibility(View.VISIBLE);
                                textMessenger.setVisibility(View.GONE);
                                textProfiles.setVisibility(View.GONE);
                                fragment = new HomeFragment();
                                break;
                            case R.id.action_profiles:
                                textHome.setVisibility(View.GONE);
                                textMessenger.setVisibility(View.GONE);
                                textProfiles.setVisibility(View.VISIBLE);
                                fragment = new ProfileFragment();
                                break;
                        }
                        final FragmentTransaction transaction =
                                fragmentManager.beginTransaction();
                                transaction
                                        .setCustomAnimations(R.anim.left_entry_transition, R.anim.right_exit_transition, R.anim.right_entry_transition, R.anim.left_exit_transition)
                                        .replace(R.id.frame_fragmentholder, fragment)
                                        .commit();
                        return true;
                    }
                }
        );
    }

    public void onClick(View view){
        Intent i = new Intent(this, Restaurants.class);
        startActivity(i);
    }
}
