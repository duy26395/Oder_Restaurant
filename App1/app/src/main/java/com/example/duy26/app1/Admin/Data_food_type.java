package com.example.duy26.app1.Admin;

public class Data_food_type {
    int id_tpye;
    String Name;
    private boolean isSelected;

    public Data_food_type(int id_tpye, String name) {
        this.id_tpye = id_tpye;
        Name = name;
        this.isSelected = isSelected;
    }
    public Data_food_type(String name)
    {
        Name = name;
    }

    public int getId_tpye() {
        return id_tpye;
    }

    public void setId_tpye(int id_tpye) {
        this.id_tpye = id_tpye;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
