package com.example.duy26.app1.Admin;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Fragment_bill_MN extends Fragment {
    RecyclerView recyclerView;
    Connectionclass connectionclass;
    Adapter_bill_mn adapter_bill_mn;
    private Button delete;
    private ArrayList<Data_mn_BILL_details> data;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_manager, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_bill_mn);
        recyclerView.setHasFixedSize(true);

        connectionclass = new Connectionclass();

        getdata_Server();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter_bill_mn = new Adapter_bill_mn(data, getContext(), new Click_data_bill_details() {

            @Override
            public void ItemClick(Data_mn_BILL_details user, int position) {
                Intent intent = new Intent(getActivity(), Manager_Bill_Details.class);
                intent.putExtra("ID",Integer.toString(user.getIdbill()));
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter_bill_mn);

        delete = (Button) view.findViewById(R.id.bill_mn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Xoá Nhân Viên");
                alertDialog.setMessage("Bạn có muốn xoá không?");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Data_mn_BILL_details number : data) {
                            if (number.isSelected()) {

                                Connectionclass connectionclass;
                                connectionclass = new Connectionclass();
                                Connection connection = connectionclass.CONN();

                                String query = "DELETE FROM [Bill] WHERE idbill =? ";
                                try {
                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.setInt(1, number.getIdbill());

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

        return view;
    }

    private void getdata_Server() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();

        data = new ArrayList<Data_mn_BILL_details>();
        String query22 = "SELECT [Bill].idbill,[Bill].date_bill,[Employess].Name_employess,[User].Name FROM [Bill],[User],[Employess]";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query22);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data.add(new Data_mn_BILL_details(resultSet.getInt("idbill"),resultSet.getString("date_bill"),
                                resultSet.getString("Name_employess"),resultSet.getString("Name")));

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
    public void onResume() {
        super.onResume();
        data.clear();
        getdata_Server();
        adapter_bill_mn.update_Bill(data);
    }
}
