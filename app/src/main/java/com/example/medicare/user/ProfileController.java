package com.example.medicare.user;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.medicare.clinic.Clinic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileController {

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;

    private boolean allowNotification;
    private boolean isLoggingOut;
    private User currentUser;

    public interface FirebaseSuccessListener{
        void onDataCompleted(boolean isDataCompleted);
    }

    public ProfileController(){

        database = FirebaseDatabase.getInstance("https://clinicinfo-e71e4-default-rtdb.asia-southeast1.firebasedatabase.app");
    }



    public void notificationAllowance(boolean allowNotification){

    }

    public void updateCurrentUser(User curUser){

    }

    public void logout(boolean isLoggingOut){

    }

    public User getCurrentUser(){
        databaseReference = database.getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }

}
