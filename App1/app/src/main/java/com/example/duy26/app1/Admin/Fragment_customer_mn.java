package com.example.duy26.app1.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.Data;
import com.example.duy26.app1.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Fragment_customer_mn extends Fragment {
    RecyclerView recyclerView;
    private boolean success = false;
    private String msg;
    Connectionclass connectionclass;
    Adapter_Customer_MN myAppdater;
    ArrayList<Data> data;
    private Button add, delete;
    private String insert_name, insert_phone, insert_address, insert_email,insert_id;
    private String addname, addphone, addaddress, addemail;

    public static final int REQUEST_CODE_THIS = 5;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_mn, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.manager_cusomer_reycycle);
        recyclerView.setHasFixedSize(true);

        connectionclass = new Connectionclass();
        data = new ArrayList<Data>();
        getdata_Server();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        myAppdater = new Adapter_Customer_MN(data, getContext(), new Onitemclick() {
            @Override
            public void onItem(Data_Manager_Employees data_manager_employees, int post) {

            }

            @Override
            public void click(Data data, int post) {

                Bundle bundle = new Bundle();
                bundle.putString("ID",data.getId());
                bundle.putString("NAME", data.getName());
                bundle.putString("PHONE", data.getPhonenumber());
                bundle.putString("ADDRESS", data.getAddress());
                bundle.putString("EMAIL", data.getEmail());
                Fragment_customer_insert Fragment = new Fragment_customer_insert();
                Fragment.setArguments(bundle);

                Fragment.setTargetFragment(Fragment_customer_mn.this,getTargetRequestCode());
                Fragment.show(getFragmentManager(),null);
                Log.e("PPPPIIIIIIIIIIIIIII", "jjjjj");

            }
        });

        recyclerView.setAdapter(myAppdater);

        add = (Button) view.findViewById(R.id.manager_customer_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_customer_add Fragment = new Fragment_customer_add();
                Fragment.setTargetFragment(Fragment_customer_mn.this,getTargetRequestCode());
                Fragment.show(getFragmentManager(),null);
            }
        });

        delete = (Button) view.findViewById(R.id.manager_customer_delete);
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

                        for (Data number : data) {
                            if (number.isSelected()) {

                                Connectionclass connectionclass;
                                connectionclass = new Connectionclass();
                                Connection connection = connectionclass.CONN();

                                String query = "DELETE FROM [User] WHERE iduser =? ";
                                try {
                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.setString(1, number.getId());

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


    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        getdata_Server();
        myAppdater.update(data);
    }

    private void send_data_insert() {
        insert_name.toString().trim();
        insert_phone.toString().trim();
        insert_email.toString().trim();
        insert_address.toString().trim();
        Connectionclass connectionclass = new Connectionclass();
        try
        {
            Connection connection = connectionclass.CONN();
            if (connection == null) {
                success = false;
                msg = "Kết nối thất bại, kiểm tra kết nối";
            }
            else if (insert_name.trim().equals("") || insert_phone.trim().equals("")) {
                success = false;
                msg = "không bỏ trống các trường";

            }else {
                String query_insert_cus = "SELECT * FROM [User] WHERE PhoneNumber = '" + insert_phone + "' " ;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query_insert_cus);
                if (resultSet.next()) {
                    try {
                        success = false;
                        msg = " số điện thoại đã tồn tại";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                    String query1 = "UPDATE [User] SET Name =?,PhoneNumber=?,Address=?,Email=? WHERE iduser =? ";

                    PreparedStatement preparedStatement = connection.prepareStatement(query1);
                    preparedStatement.setString(1,insert_name);
                    preparedStatement.setString(2,insert_phone);
                    preparedStatement.setString(3,insert_address);
                    preparedStatement.setString(4,insert_email);
                    preparedStatement.setString(5,insert_id);
                    preparedStatement.executeUpdate();

                        success = true;
                        Log.e("AAAAAAAAAAAAA", String.valueOf(success));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            Writer write = new StringWriter();
            e.printStackTrace(new PrintWriter(write));
            msg = write.toString();
            success = false;
        }
    }

    private void getdata_Server() {
        try {
            Connection connection = connectionclass.CONN();
            if (connection == null) {
                success = false;
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
                    msg = "found";
                    success = true;
                } else {
                    msg = "No data found";
                    success = false;
                }
            }

        } catch (Exception e)

        {
            e.printStackTrace();
            Writer write = new StringWriter();
            e.printStackTrace(new PrintWriter(write));
            msg = write.toString();
            success = false;
        }
    }
    private void send_data_add(){
        try
        {
            Connection connection = connectionclass.CONN();
            if (connection == null) {
                success = false;
                msg = "Kết nối thất bại, kiểm tra kết nối";
            }
            else if (addname.trim().equals("") || addphone.trim().equals("")) {
                success = false;
                msg = "không bỏ trống các trường";

            }else {
                String query = "SELECT * FROM [User] WHERE PhoneNumber= '" + addphone.toString() + "' ";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    try {
                        success = false;
                        msg = " số điện thoại đã tồn tại";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String insertTableSQL = "INSERT INTO [User] "
                            + "(Name,PhoneNumber,Address,Email) VALUES"
                            + "(?,?,?,?)";
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
                        preparedStatement.setString(1, addname);
                        preparedStatement.setString(2, addphone);
                        preparedStatement.setString(3, addaddress);
                        preparedStatement.setString(4, addemail);

                        preparedStatement.executeUpdate();

                        success = true;
                        Log.e("AAAAAAAAAAAAA", String.valueOf(success));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            Writer write = new StringWriter();
            e.printStackTrace(new PrintWriter(write));
            msg = write.toString();
            success = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                insert_id = data.getStringExtra("ID_KEY");
                insert_name = data.getStringExtra("NAME_KEY");
                insert_phone = data.getStringExtra("PHONE_KEY");
                insert_email = data.getStringExtra("MAIL_KEY");
                insert_address = data.getStringExtra("ADDRESS_KEY");
                send_data_insert();
                if (success == false) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "successfully", Toast.LENGTH_LONG).show();
                }
                onResume();
            }
        } else {
            if (data != null) {
                addname = data.getStringExtra("NAME_ADD");
                addphone= data.getStringExtra("PHONE_ADD");
                addemail = data.getStringExtra("MAIL_ADD");
                addaddress = data.getStringExtra("ADDRESS_ADD");
                send_data_add();
                if (success == false) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "successfully", Toast.LENGTH_LONG).show();
                }
                onResume();
            }
        }
    }


}