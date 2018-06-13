package com.example.duy26.app1.Admin;

public class Data_Manager_Employees {

    String idnv,ten, sdt,password,diachi;
    private boolean isSelected;

    public Data_Manager_Employees(String idnv, String ten, String sdt, String password, String diachi) {
        this.idnv = idnv;
        this.ten = ten;
        this.sdt = sdt;
        this.password = password;
        this.diachi = diachi;
        this.isSelected = isSelected;
    }

    public String getIdnv() {
        return idnv;
    }

    public void setIdnv(String idnv) {
        this.idnv = idnv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
