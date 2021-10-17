package com.example.medicare.medicine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MedicineDatabaseController {
    private static List<Medicine> medicineData = new ArrayList<Medicine>();
    private static String medicineURLstring = "https://data.gov.sg/api/action/datastore_search?resource_id=3ee20559-372d-42f0-bde9-245e21f7f39b&limit=5621";

    public MedicineDatabaseController() {}

    public static void updateMedicineDatabase() {
        if (!medicineData.isEmpty()) {
            medicineData.clear();
        }
        try {
            URL url = new URL(medicineURLstring);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line, medicineStr = "";
            while ((line = bufferedReader.readLine()) != null) {
                medicineStr = medicineStr + line;
            }
            if (!medicineStr.isEmpty()) {
                JSONObject resultsjson = new JSONObject(medicineStr).getJSONObject("result");
                JSONArray medicineRecordsjson = resultsjson.getJSONArray("records");
                
                for (int i = 0; i < medicineRecordsjson.length(); i++)
                {
                    JSONObject medObj = medicineRecordsjson.getJSONObject(i);
                    String licenseNo = medObj.getString("licence_no");
                    String medicineName = capitalizeWord(cleanString(medObj.getString("product_name")));
                    String dosageForm = capitalizeWord(cleanString(medObj.getString("dosage_form")));
                    String ingredients = capitalizeWord(insertComma(cleanString(medObj.getString("active_ingredients"))));
                    String manufacturer = capitalizeWord(cleanString(medObj.getString("manufacturer")));

                    Medicine medicine = new Medicine(licenseNo, medicineName, dosageForm, ingredients, manufacturer);
                    medicineData.add(medicine);
                }
            }
            else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getMedicineData() {
        List<String[]> medicineDataStr = new ArrayList<>();
        for (int i = 0; i < medicineData.size(); i++) {
            Medicine medicine = medicineData.get(i);
            String[] medicineInfo = new String[]{medicine.getMedicineName(), medicine.getDosageForm(), medicine.getActiveIngredients(), medicine.getManufacturer()};
            medicineDataStr.add(medicineInfo);
        }
        return medicineDataStr;
    }

    //cleans string from api
    public static String cleanString(String str) {
        String newStr = str;
        if (!(Character.isLetter(str.charAt(0)) || Character.isDigit(str.charAt(0)))){
            newStr = newStr.substring(1, str.length()-1);
        }
        if (!(Character.isLetter(str.charAt(str.length()-1)) || Character.isDigit(str.charAt(str.length()-1)))){
            newStr = newStr.substring(0, str.length()-2);
        }
        return newStr;
    }

    public static String insertComma (String str){
        String newStr = str.replaceAll("&&", ", ");
        return newStr;
    }
    public static String capitalizeWord (String str){
        String words[]=str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst.toLowerCase()+" ";
        }
        return capitalizeWord.trim();
    }
}