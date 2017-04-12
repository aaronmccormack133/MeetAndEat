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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.meetandeat.meetandeat.R.id.button;

//http://blog.iamsuleiman.com/using-bottom-navigation-view-android-design-support-library/
//https://github.com/1priyank1/BottomNavigation-Demo

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager myPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;
    private Button myButton;
    private FirebaseAuth firebaseAuth;
    private Button logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(this, Login.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //Log Out Button
        logOutBtn = (Button) findViewById(R.id.logOutBtn);

        //fragments
        myPager = (ViewPager) findViewById(R.id.Pager);
        myPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));

        //navigation
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Nullable
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_messenger:
                                myPager.setCurrentItem(0);
                                break;
                            case R.id.action_restaurants:
                                myPager.setCurrentItem(1);
                                break;
                            case R.id.action_profiles:
                                myPager.setCurrentItem(2);
                                break;
                        }
                        return true;
                    }
                }
        );
        //https://github.com/jaisonfdo/BottomNavigation
        myPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        logOutBtn.setOnClickListener(this);
        myButton = (Button) findViewById(R.id.Button);
    }

    public class SamplePagerAdapter extends FragmentPagerAdapter {
        public SamplePagerAdapter(FragmentManager fragM) {
            super(fragM);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new MessageFragment();

            } else if (position == 1) {
                return new HomeFragment();
            } else {
                return new ProfileFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public void restFinder(View view){
        Intent i = new Intent(this, restaurantFinder.class);
        startActivity(i);
    }

    public void findSelection(View view){
        Intent j = new Intent(this, peopleSelection.class);
        startActivity(j);
    }

    public void loginBtn(View view) {
        Intent k = new Intent(this, Login.class);
        startActivity(k);
    }
    @Override
    public void onClick(View view){
        if(view == logOutBtn){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, Login.class));
        }
    }
}
