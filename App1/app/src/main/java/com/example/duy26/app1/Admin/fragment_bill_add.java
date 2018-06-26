package com.example.duy26.app1.Admin;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.Data;
import com.example.duy26.app1.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class fragment_bill_add extends DialogFragment  {
    RecyclerView recyclerView;
    Connectionclass connectionclass;
    private static final int THIS_REQUEST = 10;
    ArrayList<Data> data;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private Adapter_Customer_MN adapter_customer_mn;
    private MaterialBetterSpinner spinner;
    private String name_gr,id_gr,id_cus;
    private pass_data_bill_add pass_data_bill_add;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mn_bill_add, container, false);

        recyclerView = view.findViewById(R.id.id_recycle_bill_add);
        recyclerView.setHasFixedSize(true);

        connectionclass = new Connectionclass();
        data = new ArrayList<Data>();
        getdata_Server();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
         adapter_customer_mn = new Adapter_Customer_MN(data, getContext(), new Onitemclick() {
             @Override
             public void onItem(Data_Manager_Employees data_manager_employees, int post) {

             }

             @Override
             public void click(Data data, int post) {

             }
         });
         recyclerView.setAdapter(adapter_customer_mn);
        view.findViewById(R.id.bill_ad_btnclos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                onDestroyView();
            }
        });

        arrayList = new ArrayList<String>();
        get_data();
        spinner = view.findViewById(R.id.bill_add_sniper);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(arrayAdapter);
        spinner.setHint("Chọn Nhân Viên");
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                name_gr = (String) parent.getItemAtPosition(position).toString().trim();
            }
        });
        Button confirm = view.findViewById(R.id.bill_add_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_gr =  get_defaul_name(name_gr);
                chek_box();
                if(id_gr == null || id_cus == null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Error...");
                    builder.setMessage("Không bỏ trống các trường");
                    final AlertDialog alert = builder.create();
                    alert.show();
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            timer.cancel();
                        }
                    },3500);// 3s
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("ID_USER", id_cus);
                    intent.putExtra("ID_EMP", id_gr);
                    getTargetFragment().onActivityResult(getTargetRequestCode(),THIS_REQUEST,intent);
                    Toast.makeText(getActivity(),"Add Successfully",Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    private void chek_box() {
        int i = 0;
        for (Data number : data) {
            if (number.isSelected()) { i++; }
        }
        if (i == 1) {

            for (Data number : data) {
                if (number.isSelected()) {
                    id_cus = String.valueOf(number.getId());
                }
            }
        } else {
            Toast.makeText(getActivity(),"Chọn 1 để thực hiện",Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialogFragment = getDialog();
        if (dialogFragment != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialogFragment.getWindow()).setLayout(width, height);
        }
    }
    private String get_defaul_name(String hihi) {

        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();
        try {
            String query5 = "SELECT idemployess \n" +
                    "  FROM [Employess]\n" +
                    "  WHERE Name_employess = N'" + hihi.trim() + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query5);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        id_gr = resultSet.getString("idemployess");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){}
        return id_gr;
    }

    private void get_data() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();
        try {
            String query5 = "SELECT Name_employess FROM [Employess]";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query5);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        arrayList.add(resultSet.getString("Name_employess"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception ignored) {

        }
    }

    private void getdata_Server() {
        try {
            Connection connection = connectionclass.CONN();
            if (connection == null) {

            } else {
                String query = "SELECT iduser,Name,PhoneNumber,Address,Email FROM [User]";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        try {
                            data.add(new Data(resultSet.getString("iduser"), resultSet.getString("Name"),
                                    resultSet.getString("PhoneNumber"), resultSet.
                                    getString("Address"), resultSet.getString("Email")));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {

                }
            }

        } catch (Exception e)

        {
            e.printStackTrace();
            Writer write = new StringWriter();
            e.printStackTrace(new PrintWriter(write));

        }
    }

}
