package com.example.medicare.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//class responsible for displaying items inside RecyclerView
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    // for medicine search result, each element in list has format
    // {"name", "dosage form", "ingredients", "manufacturers"}
    // for clinic search result, each element in list has format
    // {"name", "clinic ave rating", "clinic distance", "clinic address"}
    // {"name", "clinic ave rating", "clinic distance", "clinic address"}
    List<String[]> searchResults;
    List<String[]> allResultsUnfiltered;
    boolean medicineSearch;

    public RecyclerAdapter(List<String[]> searchResults, boolean medicineSearch) {
        this.searchResults = searchResults;
        this.allResultsUnfiltered = searchResults;
        this.medicineSearch = medicineSearch;
    }

    public static List<String[]> cloneList(List<String[]> list) {
        List<String[]> clone = new ArrayList<String[]>(list.size());
        for (String[] item : list) clone.add(item.clone());
        return clone;
    }

    @NonNull
    @Override
    //controls displaying items in RecyclerView
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (medicineSearch) {
            view = layoutInflater.inflate(R.layout.medicine_search_result_item, parent, false);
        }
        else {
            view = layoutInflater.inflate(R.layout.clinic_search_result_item, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (medicineSearch) {
            holder.medicineName.setText(searchResults.get(position)[0]);
            holder.dosage.setText(searchResults.get(position)[1]);
            holder.ingredientsList.setText(searchResults.get(position)[2]);
            holder.medicineManufacturer.setText("Manufacturers: " + searchResults.get(position)[3]);
        }
        else {
            holder.clinicName.setText(searchResults.get(position)[6]);
            float rating=Float.parseFloat(searchResults.get(position)[8]);
            holder.aveRating.setRating(rating);
            holder.clinicDistance.setText(searchResults.get(position)[4]+" km");
            holder.clinicAddress.setText(searchResults.get(position)[1]);
        }
    }

    @Override
    //returns the number of items inside RecyclerView
    public int getItemCount() {
        return searchResults.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String[]> filteredList = new ArrayList<>();

            //checks if there is no search input
            if (charSequence.toString().isEmpty()) {
                //add all entries into list
                filteredList.addAll(allResultsUnfiltered);
            } else {
                for (String[] searchItem: allResultsUnfiltered) {
                    for (String searchItemInfo: searchItem) {
                        if (searchItemInfo.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(searchItem);
                            break;
                        }
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }
        //run on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            searchResults = new ArrayList<>();
            searchResults.addAll((Collection<? extends String[]>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView medicineName, dosage, ingredientsList, medicineManufacturer;
        TextView clinicName, clinicDistance, clinicAddress;
        RatingBar aveRating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if (medicineSearch) {
                medicineName = itemView.findViewById(R.id.medicineNameSearch);
                dosage = itemView.findViewById(R.id.medicineDosageSearch);
                ingredientsList = itemView.findViewById(R.id.medicineIngredientsSearch);
                medicineManufacturer = itemView.findViewById(R.id.medicineManufacturerSearch);
            }
            else {
                clinicName = itemView.findViewById(R.id.clinicNameSearch);
                aveRating = itemView.findViewById(R.id.ratingSearch);
                clinicDistance = itemView.findViewById(R.id.clinicDistanceSearch);
                clinicAddress = itemView.findViewById(R.id.clinicAddressSearch);
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(itemView.getContext(), searchResults.get(getAdapterPosition())[0] + "  clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}

