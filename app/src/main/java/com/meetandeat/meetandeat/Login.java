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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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

    private Button fbbutton;
    private Button bLogIn;
    private EditText etUsername;
    private EditText etPassword;
    private Button registerBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListen;
    Firebase firebaseRef = new Firebase("https://meet-and-eat-163108.firebaseio.com/");

    private static CallbackManager callbackmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //start the main activity
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogIn = (Button) findViewById(R.id.bLogin);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Login.this, RegisterActivity.class);
                Login.this.startActivity(registerIntent);
            }
        });

        LoginButton fbbutton = (LoginButton) findViewById(R.id.button);

        //Facebook Button onClickListener
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = firebaseAuth.getCurrentUser();
        /*if(mUser != null){
            //User is signed in
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            String uid = firebaseAuth.getCurrentUser().getUid();
            String image = firebaseAuth.getCurrentUser().getPhotoUrl().toString();
            intent.putExtra("user_id", uid);
            if(image!=null || image != ""){
                intent.putExtra("profile_picture", image);
            }
            startActivity(intent);
            finish();
        }*/

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

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackmanager = CallbackManager.Factory.create();
        fbbutton.setReadPermissions("email", "public_profile");
        fbbutton.registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                signInWithFacebook(loginResult.getAccessToken());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback(){
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response){
                                try{
                                    String name = object.getString("name").toString();
                                    String profilePicture = "https://graph.facebook.com/"+loginResult.getAccessToken().getUserId()+"/picture?type=large";
                                    Log.d("Image", profilePicture);
                                    Log.d("Name", name);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields","name, profilePicture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
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
                            /*
                            String uid = task.getResult().getUser().getUid();
                            String name = task.getResult().getUser().getDisplayName();
                            String email = task.getResult().getUser().getEmail();
                            //String image = task.getResult().getUser().getPhotoUrl().toString();
                            User user = new User(uid, name, email, null);
                            firebaseRef.child(uid).setValue(user);
                            */
                            Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                            //intent.putExtra("user_id", uid);
                            //intent.putExtra("profile_picture", image);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
    public void openRegister(View view){
        Intent l = new Intent(this, RegisterActivity.class);
        startActivity(l);
    }

    public void logInClick(View view) {
        userLogin();
    }
}