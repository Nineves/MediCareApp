package com.example.medicare.medicine;

public class Medicine {
    private String licenseNo;
    private String medicineName;
    private String dosageForm;
    private String activeIngredients;
    private String manufacturer;

    public Medicine (String licenseNo, String medicineName, String dosageForm, String activeIngredients, String manufacturer) {
        this.licenseNo = licenseNo;
        this.medicineName = medicineName;
        this.dosageForm = dosageForm;
        this.activeIngredients = activeIngredients;
        this.manufacturer = manufacturer;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(String activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
