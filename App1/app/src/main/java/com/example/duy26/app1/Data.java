package com.example.duy26.app1;

public class Data {
    String id;
    String name;
    String phonenumber,Address,email;
    private boolean isSelected;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Data(String id, String name, String phonenumber, String address, String email) {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        Address = address;
        this.email = email;
        this.isSelected = isSelected;
    }

}
