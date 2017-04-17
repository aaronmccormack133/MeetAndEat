package com.meetandeat.meetandeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class EditProfileActivity extends AppCompatActivity {
    private EditText user_profile_name;
    private EditText user_profile_age;
    private EditText user_profile_short_bio;
    private ImageButton submitDetailBtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_profile );

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        user_profile_age = (EditText) findViewById(R.id.user_profile_age);
        user_profile_name = (EditText) findViewById(R.id.user_profile_name);
        user_profile_short_bio = (EditText) findViewById(R.id.user_profile_short_bio);

        submitDetailBtn = (ImageButton) findViewById(R.id.submitDetailsBtn);

        Intent l = new Intent();
    }

    private void checkUserDetails(){
        String age = user_profile_age.getText().toString().trim();
        String name = user_profile_name.getText().toString().trim();
        String bio = user_profile_short_bio.getText().toString();

        if(TextUtils.isEmpty(age)){
            Toast.makeText(this, "Please enter your age!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(bio)){
            Toast.makeText(this, "Please enter something about yourself!", Toast.LENGTH_SHORT).show();
        }
        else{
            setUserInformation();
        }
    }

    private void setUserInformation(){
        String age = user_profile_age.getText().toString().trim();
        String name = user_profile_name.getText().toString().trim();
        String bio = user_profile_short_bio.getText().toString().trim();

        UserInformation userInfo = new UserInformation(name, age, bio);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInfo)
            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
    }

    public void setDetails(View view){
        checkUserDetails();
    }
}
