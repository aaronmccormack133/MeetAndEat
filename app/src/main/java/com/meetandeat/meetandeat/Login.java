package com.meetandeat.meetandeat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.FullWallet;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

//Facebook firebase auth: http://androidbash.com/firebase-classic-email-login-facebook-login-android/

public class Login extends AppCompatActivity {

    private LoginButton fbbutton;
    private Button bLogIn;
    private EditText etUsername;
    private EditText etPassword;
    private Button registerBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListen;
    Firebase firebaseRef = new Firebase("https://meet-and-eat-163108.firebaseio.com/");

    private CallbackManager callbackmanager = null;
    private AccessTokenTracker mTracker = null;
    private ProfileTracker mProfileTracker = null;
    public static final String PARCEL_KEY = "parcel_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackmanager = CallbackManager.Factory.create();

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //start the main activity
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        //bLogIn = (Button) findViewById(R.id.bLogin);
        //registerBtn = (Button) findViewById(R.id.registerBtn);
/*
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Login.this, RegisterActivity.class);
                Login.this.startActivity(registerIntent);
            }
        });
*/
        fbbutton = (LoginButton) findViewById(R.id.button);
        fbbutton.registerCallback(callbackmanager, callback);
        fbbutton.setReadPermissions("user_friends");
        //Facebook Button onClickListener
        firebaseAuth = FirebaseAuth.getInstance();

        //FirebaseUser mUser = firebaseAuth.getCurrentUser();
        //if(mUser != null){
/*
            //User is signed in
            Intent fbIntent = new Intent(getApplicationContext(), MainActivity.class);
            String uid = firebaseAuth.getCurrentUser().getUid();
            String profileImage = firebaseAuth.getCurrentUser().getPhotoUrl().toString();
            fbIntent.putExtra("user_id", uid);
            if(profileImage!=null || profileImage!=""){
                fbIntent.putExtra("profile_picture", profileImage);
            }
            startActivity(fbIntent);
            finish();
*/
        //}
/*
        firebaseAuthListen = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if(mUser != null){
                    //user is signed in
                }
                else{
                    //user is signed out
                }
            }
        };
*/
        mTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.v("AccessTokenTracker", "oldAccessToken" +oldAccessToken+"||"+"Current Access Token"+currentAccessToken);
            }
        };
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.v("Session Tracker", "oldProfile: "+oldProfile+"||"+"Current Profile"+currentProfile);
                ProfileFragment(currentProfile);
            }
        };
        mProfileTracker.startTracking();
        mTracker.startTracking();
        Intent k = new Intent();

        //LogIn and Register Button onClickListener
        //bLogIn.setOnClickListener(this);

    }

    private void userLogin(){
        String email = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter a valid emial address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging you in, please wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //start the main activiity
                            finish();
                            startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                        }
                    }
                });
    }

    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            ProfileFragment(profile);
            signInWithFacebook(loginResult.getAccessToken());
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }

    private void signInWithFacebook(AccessToken token){

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String fbUID = task.getResult().getUser().getUid();
                            String fbName = task.getResult().getUser().getDisplayName();
                            String fbEmail = task.getResult().getUser().getEmail();

                            User user = new User(fbUID, fbName, fbEmail, null);
                            firebaseRef.child(fbUID).setValue(user);

                            Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                            intent.putExtra("user_id", fbUID);

                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private void ProfileFragment(Profile profile){
        if(profile!=null){
            Bundle fbBundle = new Bundle();
            fbBundle.putParcelable(PARCEL_KEY, profile);
            ProfileFragment pf = new ProfileFragment();
            pf.setArguments(fbBundle);
        }
    }
    public void openRegister(View view){
        Intent l = new Intent(this, RegisterActivity.class);
        startActivity(l);
    }

    public void logInClick(View view) {
        userLogin();
    }

    @Override
    public void onStop(){
        super.onStop();
        mTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    public boolean isLoggedIn(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(isLoggedIn()){
            Profile profile = Profile.getCurrentProfile();
            ProfileFragment(profile);
        }
    }
}