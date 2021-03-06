package com.example.medicare.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medicare.MainActivity;
import com.example.medicare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText username, email, password, passwordConfirm;
    Button registerbtn;
    ImageView backbtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupui);

        username = findViewById(R.id.username_input);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        passwordConfirm = findViewById(R.id.confirmPassword_input);
        registerbtn = findViewById(R.id.register_button);
        backbtn = findViewById(R.id.backbtn2);
        progressBar = findViewById(R.id.progressBar1);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_input = username.getText().toString().trim();
                String email_input = email.getText().toString().trim();
                String password_input = password.getText().toString().trim();
                String passwordC_input = passwordConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(username_input)){
                    username.setError("Username is required!");
                    return;
                }

                if(TextUtils.isEmpty(email_input)){
                    email.setError("Email is required!");
                    return;
                }

                if(TextUtils.isEmpty(password_input)){
                    password.setError("Password is required!");
                    return;
                }

                if(TextUtils.isEmpty(passwordC_input)){
                    passwordConfirm.setError("Please confirm your password!");
                    return;
                }

                if(!passwordC_input.equals(password_input)){
                    passwordConfirm.setError("Two passwords are not identical!");
                    return;
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
                            Toast.makeText(RegisterActivity.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("UserName", username_input);
                            user.put("Email", email_input);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    //Toast.makeText(RegisterActivity.this, "User profile created", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), LaunchActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LaunchActivity.class));
            }
        });

    }
}
