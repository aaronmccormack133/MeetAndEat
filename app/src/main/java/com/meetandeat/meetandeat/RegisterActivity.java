package com.meetandeat.meetandeat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//Reference: https://www.youtube.com/watch?v=0NFwF7L-YA8

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bRegister;
    private EditText etAge;
    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private ProgressDialog progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        EditText etAge = (EditText) findViewById(R.id.etAge);
        EditText etName = (EditText) findViewById(R.id.etName);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);

        Button bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(this);


        Intent l = new Intent();
    }

    private void registerUser(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String age = etAge.getText().toString();
        String username = etUsername.getText().toString().trim();
        String name = etName.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(age)){
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setMessage("Registering User...");
        progressBar.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Registration Failed, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view){
        registerUser();
    }
}