package com.meetandeat.meetandeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.fragment;

public class MainActivity extends AppCompatActivity {

    private TextView textProfiles;
    private TextView textHome;
    private TextView textMessenger;

    private List<ProfileFragment> fragments = new ArrayList<>(3);

    private static final String TAG_FRAGMENT_MESSENGER = "tag_frag_messenger";
    private static final String TAG_FRAGMENT_HOME = "tag_frag_home";
    private static final String TAG_FRAGMENT_PROFILES = "tag_frag_prof";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        switchFragment(1,TAG_FRAGMENT_HOME);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                                switchFragment(0,TAG_FRAGMENT_MESSENGER);
                                break;
                            case R.id.action_restaurants:
                                textHome.setVisibility(View.VISIBLE);
                                textMessenger.setVisibility(View.GONE);
                                textProfiles.setVisibility(View.GONE);
                                switchFragment(1,TAG_FRAGMENT_HOME);
                                break;
                            case R.id.action_profiles:
                                textHome.setVisibility(View.GONE);
                                textMessenger.setVisibility(View.GONE);
                                textProfiles.setVisibility(View.VISIBLE);
                                switchFragment(2,TAG_FRAGMENT_PROFILES);
                                break;
                        }
                        return true;
                    }
                }
        );
    }

    //http://blog.iamsuleiman.com/using-bottom-navigation-view-android-design-support-library/
    private void buildFragmentList() {
        ProfileFragment messegeFragment = buildFragment("Messenger");
        ProfileFragment homeFragment = buildFragment("Home");
        ProfileFragment profilesFragment = buildFragment("Profile");

        fragments.add(messegeFragment);
        fragments.add(homeFragment);
        fragments.add(profilesFragment);
    }

    private ProfileFragment buildFragment(String title){
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ProfileFragment.ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void switchFragment(int pos, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_fragmentholder, fragment)
                .commit();
    }

    public void onClick(View view){
        Intent i = new Intent(this, Restaurants.class);
        startActivity(i);
    }
}
