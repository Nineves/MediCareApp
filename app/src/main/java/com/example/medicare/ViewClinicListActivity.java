package com.example.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewClinicListActivity extends AppCompatActivity {
    private RecyclerView clinicResultList;
    private DatabaseReference mClinicDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mClinicDatabase = FirebaseDatabase.getInstance().getReference("clinics");
        Intent i=getIntent();
        firebaseClinicSearch();
        clinicResultList=findViewById(R.id.clinicSearchResult);
        clinicResultList.setHasFixedSize(true);
        clinicResultList.setLayoutManager(new LinearLayoutManager(this));


    }

    private void firebaseClinicSearch(){

        FirebaseRecyclerAdapter<Clinic, ClinicsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Clinic, ClinicsViewHolder>(

                Clinic.class,
                R.layout.clinic_search_result_item,
                ClinicsViewHolder.class,
                mClinicDatabase

        ) {
            protected void populateViewHolder(ClinicsViewHolder viewHolder, Clinic c, int position){
                Float rating=(Float)c.getAverageRating();
                Double distance=c.getDistance();

                viewHolder.setDetails(c.getClinicName(),rating,c.getClinicAddress(),distance.toString());

            }
        };

        clinicResultList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ClinicsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ClinicsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setDetails(String clinicName,Float clinicRating,String clinicAddress,String clinicDistance){
            TextView clinic_name=(TextView) mView.findViewById(R.id.clinicNameSearch);
            RatingBar clinic_rating=(RatingBar) mView.findViewById(R.id.ratingSearch);
            TextView clinic_address=(TextView) mView.findViewById(R.id.clinicAddressSearch);
            TextView clinic_distance=(TextView) mView.findViewById(R.id.clinicDistanceSearch);

            clinic_name.setText(clinicName);
            clinic_rating.setRating(clinicRating);
            clinic_address.setText(clinicAddress);
            clinic_distance.setText(clinicDistance);

        }
    }
}
