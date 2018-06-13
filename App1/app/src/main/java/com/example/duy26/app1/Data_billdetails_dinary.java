package com.example.duy26.app1;

public class Data_billdetails_dinary {

    String ten,gia,sluong;

    public Data_billdetails_dinary(String ten, String sluong, String gia) {
        this.ten = ten;
        this.gia = gia;
        this.sluong = sluong;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getSluong() {
        return sluong;
    }

    public void setSluong(String sluong) {
        this.sluong = sluong;
    }
}
