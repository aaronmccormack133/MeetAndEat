package com.meetandeat.meetandeat;

import android.support.design.widget.BottomNavigationView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.annotation.Nullable;

import static com.meetandeat.meetandeat.R.styleable.MenuItem;

public class MainActivity extends AppCompatActivity {

    private TextView textProfiles;
    private TextView textHome;
    private TextView textMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                            case R.id.action_restaurants:
                                textHome.setVisibility(View.VISIBLE);
                                textMessenger.setVisibility(View.GONE);
                                textProfiles.setVisibility(View.GONE);
                                break;
                            case R.id.action_messenger:
                                textHome.setVisibility(View.GONE);
                                textMessenger.setVisibility(View.VISIBLE);
                                textProfiles.setVisibility(View.GONE);
                                break;
                            case R.id.action_profiles:
                                textHome.setVisibility(View.GONE);
                                textMessenger.setVisibility(View.GONE);
                                textProfiles.setVisibility(View.VISIBLE);
                                break;
                        }
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
