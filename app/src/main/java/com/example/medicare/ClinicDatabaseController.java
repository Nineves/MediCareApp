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
import java.util.List;

public class ClinicDatabaseController {

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private static List<Clinic> clinicList = new ArrayList<Clinic>();

    public interface FirebaseSuccessListener{
        void onDataCompleted(boolean isDataCompleted);

    }

    public ClinicDatabaseController(){

        database = FirebaseDatabase.getInstance("https://clinicinfo-e71e4-default-rtdb.asia-southeast1.firebasedatabase.app");
    }

    public List<Clinic> getClinicArrayList() {
        return clinicList;
    }

    public void readDataFromClinic(final Context context, final  FirebaseSuccessListener firebaseSuccessListener){
        databaseReference = database.getReference("clinics");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cAccessCode,cAddress, cContactNumber, cName, cOpeningHours,cWebsite,cRating;
                Double cLatitude, cLongitude;
                Long cDistance;

                for(DataSnapshot mSnapshot:snapshot.getChildren()){
                    cAccessCode =(String) mSnapshot.child("access_code").getValue();
                    cAddress = (String) mSnapshot.child("address").getValue();
                    cContactNumber = String.valueOf(mSnapshot.child("contact_number").getValue());
                    cDistance = (Long) mSnapshot.child("distance").getValue();
                    cLatitude = (Double) mSnapshot.child("latitude").getValue();
                    cLongitude = (Double) mSnapshot.child("longitude").getValue();
                    cName = (String) mSnapshot.child("name").getValue();
                    cOpeningHours = (String) mSnapshot.child("opening_hours").getValue();
                    cRating = (String) mSnapshot.child("rating").getValue();
                    cWebsite = (String) mSnapshot.child("website").getValue();

                    Clinic clinic = new Clinic(cAccessCode, cAddress,cContactNumber,cDistance,cLatitude,cLongitude,cName,cOpeningHours,cRating,cWebsite);
                    clinicList.add(clinic);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }

}
