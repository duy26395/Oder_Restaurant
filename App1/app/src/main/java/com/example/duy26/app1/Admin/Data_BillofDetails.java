package com.example.duy26.app1.Admin;

public class Data_BillofDetails {
    int id;
    String ten_food,number,price;
    private boolean isSelected;

    public Data_BillofDetails(int id,String ten_food, String number, String price) {
        this.id = id;
        this.ten_food = ten_food;
        this.number = number;
        this.price = price;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_food() {
        return ten_food;
    }

    public void setTen_food(String ten_food) {
        this.ten_food = ten_food;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
