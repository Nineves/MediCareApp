package com.example.medicare.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicare.R;
import com.example.medicare.login.LoginActivity;
import com.example.medicare.login.ResetPasswordActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {
    private User currentUser;
    ProfileController pc=new ProfileController();
    TextView userNameText,emailText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i=getIntent();
        setContentView(R.layout.profile_page);
        currentUser= new User("ywang109@e.ntu.edu.sg","Nineves");

        userNameText=findViewById(R.id.textViewUser);
        emailText=findViewById(R.id.textViewEmail);

        userNameText.setText(currentUser.getUsername());
        emailText.setText(currentUser.getEmail());
        
        Button button1=(Button)findViewById(R.id.changePasswordButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserProfileActivity.this, ResetPasswordActivity.class);
                startActivity(i);
            }
        });

        Button button2=(Button) findViewById(R.id.logoutButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
