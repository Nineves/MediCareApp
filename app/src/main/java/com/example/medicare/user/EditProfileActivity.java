package com.example.medicare.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicare.R;

public class EditProfileActivity extends AppCompatActivity {

    private User currentUser;
    TextView userNameText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();
        setContentView(R.layout.edit_profile);
        Button DONE=findViewById(R.id.doneButton);
        DONE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUsername();
            }
        });

        Button CANCEL=findViewById(R.id.neumorphButton3);
        CANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void editUsername(){
        EditText text = (EditText)findViewById(R.id.editTextTextPersonName);
        String newName = text.getText().toString();
        userNameText=findViewById(R.id.textView2);
        userNameText.setText(newName);
    }



}
