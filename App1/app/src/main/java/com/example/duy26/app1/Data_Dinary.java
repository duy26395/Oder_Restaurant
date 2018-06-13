package com.example.duy26.app1;

public class Data_Dinary {
    String date;
    int id_bill,id_employess,id_user;

    public Data_Dinary(int id_bill,String date, int id_employess, int id_user) {
        this.date = date;
        this.id_bill = id_bill;
        this.id_employess = id_employess;
        this.id_user = id_user;
    }

    public int getId_bill() {
        return id_bill;
    }

    public void setId_bill(int id_bill) {
        this.id_bill = id_bill;
    }

    public int getId_employess() {
        return id_employess;
    }

    public void setId_employess(int id_employess) {
        this.id_employess = id_employess;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
