package com.example.duy26.app1.Admin;

public class Data_Food_delete {

    String id_foood,Name,price;
    private boolean isSelected;

    public Data_Food_delete(String id_foood, String name, String price) {
        this.id_foood = id_foood;
        Name = name;
        this.price = price;
    }

    public String getId_foood() {
        return id_foood;
    }

    public void setId_foood(String id_foood) {
        this.id_foood = id_foood;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
