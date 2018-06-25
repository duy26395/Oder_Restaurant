package com.example.duy26.app1.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Manager_Bill_Details extends AppCompatActivity implements Bill_ofDetails_Insert_interface {
    RecyclerView recyclerView;
    Connectionclass connectionclass;
    private ArrayList<Data_BillofDetails> data;
    private int i;
    private String idbill;
    private Adapter_BillofDetails_mn adapter_bill_mn;
    String tong = "0";
    TextView total;
    private int REQUEST_INSERT = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__bill__details);

        recyclerView = findViewById(R.id.mn_bill_details_recycler);
        recyclerView.setHasFixedSize(true);

        connectionclass = new Connectionclass();
        Intent intent = this.getIntent();
        idbill = intent.getStringExtra("ID");
        Log.e("IDBILL_________MN",idbill);
        i = Integer.parseInt(idbill);

        getdata_Server();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter_bill_mn = new Adapter_BillofDetails_mn(data,getApplicationContext(), new Click_BillofDetails() {
            @Override
            public void itemclick(Data_BillofDetails data_billofDetails, int postion) {

            }
        });
        Button add = findViewById(R.id.mn_bill_details_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("ID_BILL",idbill);
                Bill_ofDetails_ADD bill_ofDetails_add = new Bill_ofDetails_ADD();
                bill_ofDetails_add.setArguments(bundle);

                bill_ofDetails_add.show(getSupportFragmentManager(),null);
            }
        });
        recyclerView.setAdapter(adapter_bill_mn);
        Button delete = findViewById(R.id.mn_bill_details_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Manager_Bill_Details.this);
                alertDialog.setTitle("Xoá Hoá Đơn");
                alertDialog.setMessage("Bạn có muốn xoá không?");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Data_BillofDetails number : data) {
                            if (number.isSelected()) {

                                Connectionclass connectionclass;
                                connectionclass = new Connectionclass();
                                Connection connection = connectionclass.CONN();

                                String query = "DELETE FROM [Bill_details] WHERE idd =? ";
                                try {
                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.setString(1, String.valueOf(number.getId()));

                                    preparedStatement.executeUpdate();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        onResume();
                        dialog.dismiss();
                    }
                });
                alertDialog.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });
        total = findViewById(R.id.mn_bill_details_total);
        total_sever();
        total.setText(tong);
        onResume();
    }

    private void total_sever() {

        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();
        String query22 = "SELECT SUM([Bill_details].Number * [Food].Prince) AS TOTAL " +
                "FROM [Bill_details]JOIN [Food] ON [Food].idfood = [Bill_details].idfood" +
                " Where [Bill_details].idbill = '"+ i +"'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query22);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        tong = resultSet.getString("TOTAL");
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

    @Override
    protected void onResume() {
        super.onResume();
        data.clear();
        total.setText("");
        getdata_Server();
        total_sever();
        total.setText(tong);
        adapter_bill_mn.update_Billdetails(data);

    }

    private void getdata_Server() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();

        data = new ArrayList<Data_BillofDetails>();
        String query22 = "SELECT[Bill_details].idd,[Food].NameFood,[Bill_details].Number,[Food].Prince " +
                "FROM [Bill_details]JOIN [Food] ON [Food].idfood = [Bill_details].idfood" +
                " Where [Bill_details].idbill = '"+ i +"'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query22);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data.add(new Data_BillofDetails(resultSet.getInt("idd"),resultSet.getString("NameFood"),resultSet.getString("Number"),
                                resultSet.getString("Prince")));

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

    @Override
    public void result(String id_food, int number) {

        int sott = number;
        Log.e("SOOOOOOOOOTTTT",String.valueOf(sott));
    }
}
