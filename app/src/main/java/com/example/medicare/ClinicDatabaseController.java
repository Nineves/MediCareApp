package com.example.medicare;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.example.medicare.Clinic;
public class ClinicDatabaseController {

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private static ArrayList<Clinic> clinicArrayList = new ArrayList<Clinic>();

    public interface FirebaseSuccessListener{
        void onDataCompleted(boolean isDataCompleted);

    }

    public ClinicDatabaseController(){

        database = FirebaseDatabase.getInstance();
    }

    public static ArrayList<Clinic> getClinicArrayList() {
        return clinicArrayList;
    }

    public void readDataFromClinic(final Context context, final  FirebaseSuccessListener firebaseSuccessListener){
        databaseReference = database.getReference("clinics");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cAccessCode,cAddress, cContactNumber, cName, cOpeningHours,cWebsite;
                double cLatitude, cLongitude,cRating;
                int cDistance;

                for(DataSnapshot mSnapshot:snapshot.getChildren()){
                    cAccessCode =(String) mSnapshot.child("access_code").getValue();
                    cAddress = (String) mSnapshot.child("address").getValue();
                    cContactNumber = (String) mSnapshot.child("distance").getValue();
                    cDistance = (int) mSnapshot.child("distance").getValue();
                    cLatitude = (double) mSnapshot.child("latitude").getValue();
                    cLongitude = (double) mSnapshot.child("longitude").getValue();
                    cName = (String) mSnapshot.child("name").getValue();
                    cOpeningHours = (String) mSnapshot.child("opening_hours").getValue();
                    cRating = (double) mSnapshot.child("rating").getValue();
                    cWebsite = (String) mSnapshot.child("website").getValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
