package com.example.medicare.search;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicare.R;

public class MedicineInfoPageActivity extends AppCompatActivity {

    ImageButton backButton;
    TextView medicineName, medicineManufacturer, medicineIngredients, medicineDose;
    private String name, manufacturer, ingredients, dose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_info_page);

        Bundle bundle = getIntent().getExtras();

        name = bundle.getString("medicineName");
        manufacturer = bundle.getString("medicineManufacturer");
        ingredients = bundle.getString("medicineIngredients");
        dose = bundle.getString("medicineDose");

        medicineName = findViewById(R.id.title_medicine);
        medicineName.setText(name);
        medicineManufacturer = findViewById(R.id.manufacturer_content);
        medicineManufacturer.setText(manufacturer);
        medicineIngredients = findViewById(R.id.ingredients_content);
        medicineIngredients.setText(ingredients);
        medicineDose = findViewById(R.id.dosage_content);
        medicineDose.setText(dose);



        backButton = findViewById(R.id.backButtonMedInfo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
