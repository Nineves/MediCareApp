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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword1 extends AppCompatActivity {

    EditText email;
    TextView resend;
    Button sendbtn, backtologinbtn;
    TextView resendbtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword1);

        email = findViewById(R.id.email_input2);
        sendbtn = findViewById(R.id.send_button2);
        resendbtn = findViewById(R.id.resend);
        fAuth = FirebaseAuth.getInstance();
        resend = findViewById(R.id.resend);
        backtologinbtn = findViewById(R.id.backtologin);

        Toast.makeText(resetPassword1.this, "Please enter your email!", Toast.LENGTH_SHORT).show();

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String resetemail = email.getText().toString().trim();

                if(TextUtils.isEmpty(resetemail)){
                    email.setError("Email is required!");
                    return;
                }

                fAuth.sendPasswordResetEmail(resetemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(resetPassword1.this, "Reset password link has been sent to your email!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(resetPassword1.this, "Error! Reset password link is not sent!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resetemail = email.getText().toString().trim();

                if(TextUtils.isEmpty(resetemail)){
                    email.setError("Email is required!");
                    return;
                }

                fAuth.sendPasswordResetEmail(resetemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(resetPassword1.this, "Reset password link has been sent to your email!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(resetPassword1.this, "Error! Reset password link is not sent!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        backtologinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
    }
}