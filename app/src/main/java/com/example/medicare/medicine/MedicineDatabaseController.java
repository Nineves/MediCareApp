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
    private static boolean appOn = false;

    public MedicineDatabaseController() {}

    public static void updateMedicineDatabase() {
        if (!appOn) {
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
                        String licenseNo = noInfoFound(medObj.getString("licence_no"));
                        String medicineName = noInfoFound(capitalizeWord(cleanString(medObj.getString("product_name"))));
                        String dosageForm = noInfoFound(capitalizeWord(cleanString(medObj.getString("dosage_form"))));
                        String ingredients = noInfoFound(capitalizeWord(insertComma(cleanString(medObj.getString("active_ingredients")))));
                        String manufacturer = noInfoFound(capitalizeWord(insertComma(cleanString(medObj.getString("manufacturer")))));

                        Medicine medicine = new Medicine(licenseNo, medicineName, dosageForm, ingredients, manufacturer);
                        medicineData.add(medicine);
                    }
                }
                else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            appOn = true;
        }
    }

    public static String noInfoFound(String info) {
        if (info.isEmpty()) {
            info = "No information found";
        }
        return info;
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
        int strLen = str.length() - 1;
        if (!(Character.isLetter(str.charAt(0)) || Character.isDigit(str.charAt(0)))){
            newStr = newStr.substring(1, str.length()-1);
        }
        if (!(Character.isLetter(str.charAt(strLen)) || Character.isDigit(str.charAt(strLen))
            || str.charAt(strLen) == '.')){
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
        capitalizeWord = capitalizeWord.trim();

        words = capitalizeWord.split("\\(");
        capitalizeWord = "";
        for (String w:words) {
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+"(";
        }
        capitalizeWord = capitalizeWord.substring(0,capitalizeWord.length() - 1);

        words = capitalizeWord.split("\\.");
        capitalizeWord = "";
        for (String w:words) {
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+".";
        }
        capitalizeWord = capitalizeWord.substring(0,capitalizeWord.length() - 1);

        words = capitalizeWord.split("\\,");
        capitalizeWord = "";
        for (String w:words) {
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+",";
        }
        capitalizeWord = capitalizeWord.substring(0,capitalizeWord.length() - 1);

        words = capitalizeWord.split("\\/");
        capitalizeWord = "";
        for (String w:words) {
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+"/";
        }
        capitalizeWord = capitalizeWord.substring(0,capitalizeWord.length() - 1);

        /*
        String indices = "";
        String punctuation = "()/.,";
        for(int i=0; i < capitalizeWord.length(); i++) {
            int punct = punctuation.indexOf(capitalizeWord.charAt(i));
            if (punct >= 0 && Character.isLowerCase(capitalizeWord.charAt(i+1))) {
                indices += i+1;
            }
        }

        for(int i=0; i < indices.length(); i++) {
            int j = indices.charAt(i);
            capitalizeWord = capitalizeWord.substring(0, j) + Character.toUpperCase(capitalizeWord.charAt(j))
                    + capitalizeWord.substring(j+1);
        }

        */

        capitalizeWord = capitalizeWord.replace("W/W", "w/w");
        capitalizeWord = capitalizeWord.replace("W/V", "w/v");
        capitalizeWord = capitalizeWord.replace("Ml", "ml");
        capitalizeWord = capitalizeWord.replace("Mg", "mg");

        return capitalizeWord;
    }
}
