package com.example.duy26.app1.Admin;

public class Data_BillofDetails {
    String ten_food;
    int number,price;
    private boolean isSelected;

    public Data_BillofDetails(String ten_food, int number, int price) {
        this.ten_food = ten_food;
        this.number = number;
        this.price = price;
        this.isSelected = isSelected;
    }

    public String getTen_food() {
        return ten_food;
    }

    public void setTen_food(String ten_food) {
        this.ten_food = ten_food;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
