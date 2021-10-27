package com.example.medicare.search;

import android.content.Context;
import android.content.Intent;
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

import com.example.medicare.clinic.InfoDisplay;
import com.example.medicare.clinic.ItemAnimation;
import com.example.medicare.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//class responsible for displaying items inside RecyclerView
public class MedicineRecyclerAdapter extends RecyclerView.Adapter<MedicineRecyclerAdapter.ViewHolder> implements Filterable {

    // for medicine search result, each element in list has format
    // {"name", "dosage form", "ingredients", "manufacturers"}
    List<String[]> searchResults;
    List<String[]> allResultsUnfiltered;
    Context context;

    public MedicineRecyclerAdapter(Context context, List<String[]> searchResults) {
        this.context = context;
        this.searchResults = new ArrayList<>(searchResults);
        this.allResultsUnfiltered = new ArrayList<>(searchResults);
    }

    public int getSearchResultsSize() {
        return this.searchResults.size();
    }

    public void updateSearchResults(List<String[]> searchResults) {
        this.searchResults = new ArrayList<>(searchResults);
        //this.searchResults = new ArrayList<>(searchResults);
        //this.allResultsUnfiltered = searchResults;
        this.allResultsUnfiltered = new ArrayList<>(searchResults);
    }

    @NonNull
    @Override
    //controls displaying items in RecyclerView
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.medicine_search_result_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.medicineName.setText(searchResults.get(position)[0]);
        holder.dosage.setText(searchResults.get(position)[1]);
        holder.ingredientsList.setText(searchResults.get(position)[2]);
        holder.medicineManufacturer.setText("Manufacturers: " + searchResults.get(position)[3]);

        ItemAnimation.animateFadeIn(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MedicineInfoPageActivity.class);

                intent.putExtra("medicineName", searchResults.get(holder.getAdapterPosition())[0]);
                intent.putExtra("medicineDose", searchResults.get(holder.getAdapterPosition())[1]);
                intent.putExtra("medicineIngredients", searchResults.get(holder.getAdapterPosition())[2]);
                intent.putExtra("medicineManufacturer", searchResults.get(holder.getAdapterPosition())[3]);

                context.startActivity(intent);

            }
        });
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

            String str = charSequence.toString().replace("\n","");
            //checks if there is no search input
            if (str.isEmpty()) {
                //add all entries into list
                filteredList.addAll(allResultsUnfiltered);
            } else {
                for (String[] searchItem: allResultsUnfiltered) {
                    for (String searchItemInfo: searchItem) {
                        if (searchItemInfo.toLowerCase().contains(str.toLowerCase())) {
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
            //searchResults = new ArrayList<>();
            searchResults.clear();
            searchResults.addAll((Collection<? extends String[]>) filterResults.values);

            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView medicineName, dosage, ingredientsList, medicineManufacturer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            medicineName = itemView.findViewById(R.id.medicineNameSearch);
            dosage = itemView.findViewById(R.id.medicineDosageSearch);
            ingredientsList = itemView.findViewById(R.id.medicineIngredientsSearch);
            medicineManufacturer = itemView.findViewById(R.id.medicineManufacturerSearch);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(itemView.getContext(), searchResults.get(getAdapterPosition())[0] + "  clicked!", Toast.LENGTH_SHORT).show();
            /*
            Intent intent = new Intent(context, MedicineInfoPageActivity.class);

            intent.putExtra("medicineName", searchResults.get(getAdapterPosition())[0]);
            intent.putExtra("medicineManufacturer", searchResults.get(getAdapterPosition())[1]);
            intent.putExtra("medicineIngredients", searchResults.get(getAdapterPosition())[2]);
            intent.putExtra("medicineDose", searchResults.get(getAdapterPosition())[3]);

            context.startActivity(intent);
            */

        }
    }
}

