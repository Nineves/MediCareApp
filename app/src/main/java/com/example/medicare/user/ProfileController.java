package com.example.medicare.user;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.medicare.clinic.Clinic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;

public class ProfileController {

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private static List<User> userList = new ArrayList<User>();

    private boolean isLoggingOut;
    private User currentUser;

    public interface FirebaseSuccessListener{
        void onDataCompleted(boolean isDataCompleted);
    }

    public ProfileController(){

        database = FirebaseDatabase.getInstance("https://clinicinfo-e71e4-default-rtdb.asia-southeast1.firebasedatabase.app");
    }



    public void updateCurrentUser(User curUser){
        DatabaseReference ref=database.getReference("Users");
        ref.child("2").child("email").setValue(curUser.getEmail());
        ref.child("2").child("username").setValue(curUser.getUsername());

    }

    public User getCurrentUser(){
        databaseReference = database.getReference("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email;
                String userName;
                for(DataSnapshot mSnapshot:snapshot.getChildren()) {
                    email=(String) mSnapshot.child("email").getValue();
                    userName=(String) mSnapshot.child("username").getValue();
                    User newUser= new User(email,userName);
                    userList.add(newUser);
                    Log.e("userRead","Read user successfully");
                }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
        return userList.get(0);
    }

}
