package com.example.duy26.app1.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Manager_Bill_Details extends AppCompatActivity {
    RecyclerView recyclerView;
    Connectionclass connectionclass;
    private ArrayList<Data_BillofDetails> data;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__bill__details);

        recyclerView = (RecyclerView)findViewById(R.id.mn_bill_details_recycler);
        recyclerView.setHasFixedSize(true);

        connectionclass = new Connectionclass();
        Intent intent = this.getIntent();
        String idbill = intent.getStringExtra("ID");
        Log.e("IDBILL_________MN",idbill);
        i = Integer.parseInt(idbill);

        getdata_Server();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        Adapter_BillofDetails_mn adapter_bill_mn = new Adapter_BillofDetails_mn(data,getApplicationContext(), new Click_BillofDetails() {
            @Override
            public void itemclick(Data_BillofDetails data_billofDetails, int postion) {

            }
        });
        recyclerView.setAdapter(adapter_bill_mn);

    }

    private void getdata_Server() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();

        data = new ArrayList<Data_BillofDetails>();
        String query22 = "SELECT [Food].NameFood,[Bill_details].Number,[Bill_details].Price FROM [Bill_details],[Food] Where [Bill_details].idbill = '"+ i +"'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query22);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data.add(new Data_BillofDetails(resultSet.getString("NameFood"),resultSet.getInt("Number"),
                                resultSet.getInt("Price")));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.e("LOG____ADAPTER_BILL", String.valueOf(data));
    }
}
