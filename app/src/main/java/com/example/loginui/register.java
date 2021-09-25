package com.example.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    EditText username, email, password, passwordConfirm;
    Button registerbtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupui);

        username = findViewById(R.id.username_input);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        passwordConfirm = findViewById(R.id.confirmPassword_input);
        registerbtn = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progressBar1);

        fAuth = FirebaseAuth.getInstance();

        /*
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        */

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_input = username.getText().toString().trim();
                String email_input = email.getText().toString().trim();
                String password_input = password.getText().toString().trim();
                String passwordC_input = passwordConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(username_input)){
                    username.setError("Username is required!");
                }

                if(TextUtils.isEmpty(email_input)){
                    email.setError("Email is required!");
                    return;
                }

                if(TextUtils.isEmpty(password_input)){
                    email.setError("Password is required!");
                    return;
                }

                if(TextUtils.isEmpty(passwordC_input)){
                    passwordConfirm.setError("Please confirm your password!");
                    return;
                }

                if(!passwordC_input.equals(password_input)){
                    passwordConfirm.setError("Two passwords are not identical!");
                }

                if(password_input.length() < 6){
                    password.setError("Password must be at least 6 characters!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Register user in the firebase

                fAuth.createUserWithEmailAndPassword(email_input, password_input).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}