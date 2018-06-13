package com.example.duy26.app1.Admin;

public class Data_mn_BILL_details {
    String date,tennv,tenuser;
    int idbill;
    private boolean isSelected;


    public Data_mn_BILL_details(int idbill,String date, String tennv, String tenuser) {
        this.date = date;
        this.tennv = tennv;
        this.tenuser = tenuser;
        this.idbill = idbill;
        this.isSelected = isSelected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getTenuser() {
        return tenuser;
    }

    public void setTenuser(String tenuser) {
        this.tenuser = tenuser;
    }

    public int getIdbill() {
        return idbill;
    }

    public void setIdbill(int idbill) {
        this.idbill = idbill;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
