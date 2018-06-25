package com.example.duy26.app1.Admin;

public class Data_delete_food_clicklong {
    String name_food;
    String details;
    String image;
    String idfood;
    String gia;
    String loai;
    String name_type;
    private boolean isSelected;

    public Data_delete_food_clicklong(String name_food, String details, String image, String idfood, String gia, String loai,String name_type) {
        this.name_food = name_food;
        this.details = details;
        this.image = image;
        this.idfood = idfood;
        this.gia = gia;
        this.loai = loai;
        this.name_type = name_type;
        this.isSelected = isSelected;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdfood() {
        return idfood;
    }

    public void setIdfood(String idfood) {
        this.idfood = idfood;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLoai() {
        return loai;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
