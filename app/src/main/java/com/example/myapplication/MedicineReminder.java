package com.example.myapplication;

public class MedicineReminder {

    String medicineName, startTime, freeText, dose;
    //how many times to take the medicine each day
    int frequency;

    public MedicineReminder() {
    }

    public MedicineReminder(String medicineName, String startTime, String freeText, int frequency, String dose) {
        this.medicineName = medicineName;
        this.startTime = startTime;
        this.freeText = freeText;
        this.frequency = frequency;
        this.dose = dose;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
