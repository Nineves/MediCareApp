package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MRAdapter extends RecyclerView.Adapter<MRAdapter.MyViewHolder>  {

    Context context;
    ArrayList<MedicineReminder> medicineReminder;

    public MRAdapter(Context c, ArrayList<MedicineReminder> p){
        context = c;
        medicineReminder = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mr, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.medicineName.setText(medicineReminder.get(i).getMedicineName());
        myViewHolder.freeText.setText(medicineReminder.get(i).getFreeText());
        myViewHolder.timeandFrequency.setText(medicineReminder.get(i).getStartTime() +
                                                medicineReminder.get(i).getDose() + " /times, " +
                                                medicineReminder.get(i).getFrequency() + " times/day");

    }

    @Override
    public int getItemCount() {
        return medicineReminder.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView medicineName, timeandFrequency, freeText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            medicineName = (TextView)itemView.findViewById(R.id.medicineName);
            timeandFrequency = (TextView)itemView.findViewById(R.id.timeandFrequency);
            freeText = (TextView)itemView.findViewById(R.id.freeText);
        }
    }
}
