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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.client.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

//http://blog.iamsuleiman.com/using-bottom-navigation-view-android-design-support-library/
//https://github.com/1priyank1/BottomNavigation-Demo

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    private ViewPager myPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;
    private Button myButton;
    private Button logOutBtn;
    //Get the Image from facebook to display on the profile
    private TextView user_profile_name;
    private String name;
    private TextView user_profile_short_bio;
    private String bio;
    private TextView user_profile_age;
    private String age;
    private ImageButton user_profile_photo;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = (new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent login = new Intent(MainActivity.this, Login.class);
                    startActivity(login);
                }
            }
        });
/*
//Not Working
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
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

        //logOutBtn.setOnClickListener(this);
        myButton = (Button) findViewById(R.id.Button);

        user_profile_age = (TextView) findViewById(R.id.user_profile_age);
        user_profile_short_bio = (TextView) findViewById(R.id.user_profile_short_bio);
        user_profile_name = (TextView) findViewById(R.id.user_profile_name);

    }

    private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            UserInformation uInfo = new UserInformation();
            uInfo.setUserInfoAge(ds.child(userID).getValue(UserInformation.class).getUserInfoAge());
            uInfo.setUserInfoBio(ds.child(userID).getValue(UserInformation.class).getUserInfoBio());
            uInfo.setUserInfoName(ds.child(userID).getValue(UserInformation.class).getUserInfoName());

            Log.d(TAG, "showData: age: "+uInfo.getUserInfoAge());
            Log.d(TAG, "showData: bio: "+uInfo.getUserInfoBio());
            Log.d(TAG, "showData: name: "+uInfo.getUserInfoName());


            user_profile_age.setText(uInfo.getUserInfoAge());
            user_profile_short_bio.setText(uInfo.getUserInfoBio());
            user_profile_name.setText(uInfo.getUserInfoName());
        }
    }
/*
    public void defineButtons(){

        findViewById( R.id.firstButton).setOnClickListener( buttonClickListener );
        findViewById( R.id.secondButton ).setOnClickListener( buttonClickListener );
        findViewById( R.id.thirdButton ).setOnClickListener( buttonClickListener );
        findViewById( R.id.fourthButton ).setOnClickListener( buttonClickListener);

    }
*/

/*
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.firstButton:
                    startActivity(new Intent( MainActivity.this, buttonActivity.class ));
                    break;
                case R.id.secondButton:
                    startActivity(new Intent( MainActivity.this, buttonActivity.class ));
                    break;
                case R.id.thirdButton:
                    startActivity(new Intent( MainActivity.this, buttonActivity.class ));
                    break;
                case R.id.fourthButton:
                    startActivity(new Intent( MainActivity.this, buttonActivity.class ));
                    break;
            }

        }
    };
    */


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
    public void logOutBtn(View view){
        mAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        finish();
        startActivity(new Intent(this, Login.class));
    }
    public void openEdit(View view){
        Intent l = new Intent(this, EditProfileActivity.class);
        startActivity(l);
    }

}
