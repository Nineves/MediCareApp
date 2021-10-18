package com.example.medicare.clinic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecyclerviewHolder> {

    public CharSequence search;
    Context context;
    List<Clinic> clinicDataList;
    List<Clinic> filteredClinicDataList;

    public RecyclerviewAdapter(Context context, List<Clinic> clinicDataList) {
        this.context = context;
        this.clinicDataList = clinicDataList;
        this.filteredClinicDataList = clinicDataList;
    }

    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.clinic_search_result_item, parent, false);
        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, final int position) {

        holder.clinicName.setText(filteredClinicDataList.get(position).getName());
        holder.clinicAdd.setText(filteredClinicDataList.get(position).getAddress());
        holder.clinicDist.setText(String.valueOf(filteredClinicDataList.get(position).getDistance()));
        String r=filteredClinicDataList.get(position).getRating();
        Float r_double;
        if (r.equals("N.A.")){
            r_double=2.5f;
        }
        else {
            r_double=Float.parseFloat(r);
        }
        holder.clinicRating.setRating(r_double);

        ItemAnimation.animateFadeIn(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, InfoDisplay.class);
                intent.putExtra("name", filteredClinicDataList.get(position).getName());
                intent.putExtra("address", filteredClinicDataList.get(position).getAddress());
                intent.putExtra("distance",filteredClinicDataList.get(position).getDistance());
                intent.putExtra("website",filteredClinicDataList.get(position).getWebsite());
                intent.putExtra("openingHours",filteredClinicDataList.get(position).getOpening_hours());
                intent.putExtra("rating",filteredClinicDataList.get(position).getRating());
                intent.putExtra("latitude",filteredClinicDataList.get(position).getLatitude());
                intent.putExtra("longitude",filteredClinicDataList.get(position).getLongitude());
                intent.putExtra("contactNumber",filteredClinicDataList.get(position).getContact_number());
                intent.putExtra("accessCode",filteredClinicDataList.get(position).getAccess_code());
                intent.putExtra("ratingCount",filteredClinicDataList.get(position).getRating_count());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return filteredClinicDataList.size();
    }

    public static final class RecyclerviewHolder extends RecyclerView.ViewHolder {


        TextView clinicName, clinicDist,clinicAdd;
        RatingBar clinicRating;

        public RecyclerviewHolder(@NonNull View itemView) {
            super(itemView);

            clinicName=itemView.findViewById(R.id.clinicNameSearch);
            clinicDist=itemView.findViewById(R.id.clinicDistanceSearch);
            clinicAdd=itemView.findViewById(R.id.clinicAddressSearch);
            clinicRating=itemView.findViewById(R.id.ratingSearch);


        }
    }

    public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String Key = charSequence.toString();
                if(Key.isEmpty()){
                    filteredClinicDataList = clinicDataList;
                }
                else{

                    List<Clinic> lstFiltered = new ArrayList<>();
                    for(Clinic row: clinicDataList){
                        if(row.getName().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);

                        }
                    }

                    filteredClinicDataList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredClinicDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filteredClinicDataList = (List<Clinic>)filterResults.values;
                notifyDataSetChanged();

            }
        };

    }


}