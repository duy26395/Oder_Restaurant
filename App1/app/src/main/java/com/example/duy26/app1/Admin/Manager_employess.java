package com.example.duy26.app1.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.CheckBox;
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

public class Manager_employess extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Data_Manager_Employees> data_manager_employees;
    CheckBox checkBox;
    Boolean success = false;
    String msg,tenn,sdthoai,matkhau,diachii;
    Adapter_Employees_manager employees_manager;
    ProgressDialog dialog;
    Button add,delete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_manager_employess,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.manager_employees_reycycle);
        recyclerView.setHasFixedSize(true);

        data_manager_employees = new ArrayList<Data_Manager_Employees>();
        getdata_server();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        employees_manager = new Adapter_Employees_manager(data_manager_employees, getContext(), new Onitemclick() {
            @Override
            public void onItem(Data_Manager_Employees data_manager_employees, int post) {
                Intent intent =new Intent(getActivity(),Emplyess_details.class);
        Bundle bundle = new Bundle();
        bundle.putString("ID_KEY",data_manager_employees.idnv);
        bundle.putString("NAME_KEY",data_manager_employees.ten);
        bundle.putString("PHONE_KEY",data_manager_employees.sdt);
        bundle.putString("PASS_KEY",data_manager_employees.password);
        bundle.putString("ADDRESS_KEY",data_manager_employees.diachi);
        intent.putExtra("DATA",bundle);
        startActivityForResult(intent,1);
                Toast.makeText(getContext(),"SUCCESS",Toast.LENGTH_LONG).show();
            }

            @Override
            public void click(Data data, int post) {

            }
        });
        recyclerView.setAdapter(employees_manager);
        delete = (Button)view.findViewById(R.id.manager_employees_delete);
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

                        for (Data_Manager_Employees number : data_manager_employees) {
                            if (number.isSelected()) {

                                Connectionclass connectionclass;
                                connectionclass = new Connectionclass();
                                Connection connection = connectionclass.CONN();

                                String query = "DELETE FROM Employess WHERE idemployess=? ";
                                try {
                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.setString(1,number.getIdnv());
                                    preparedStatement.executeUpdate();

                                } catch (SQLException e) { e.printStackTrace(); }
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

        add = (Button)view.findViewById(R.id.manager_employees_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fg_mn_ad_employess ad_employess = new fg_mn_ad_employess();
                ad_employess.setTargetFragment(Manager_employess.this,getTargetRequestCode());
                ad_employess.show(getFragmentManager(),null);

            }
        });
        employees_manager.notifyDataSetChanged();
        return view;
    }
    private void send_data_add() {
        tenn.toString().trim();
        sdthoai.toString().trim();
        matkhau.toString().trim();
        diachii.toString().trim();
        Connectionclass connectionclass = new Connectionclass();
        try
        {
            Connection connection = connectionclass.CONN();
            if (connection == null) {
                success = false;
                msg = "Kết nối thất bại, kiểm tra kết nối";
            }
            else if (tenn.trim().equals("") || sdthoai.trim().equals("") || matkhau.trim().equals("")) {
                success = false;
                msg = "không bỏ trống các trường";

            }else {
                String query1 = "SELECT * FROM Employess WHERE Phone_Employess= '" + sdthoai + "' " +
                        "OR Name_employess = '" + tenn + "' ";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query1);
                if (resultSet.next()) {
                    try {
                        success = false;
                        msg = " số điện thoại và tài khoản đã tồn tại";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String insertTableSQL = "INSERT INTO Employess "
                            + "(Name_employess,Phone_Employess,Password_employess,Adreess_employess) VALUES"
                            + "(?,?,?,?)";
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
                        preparedStatement.setString(1, tenn);
                        preparedStatement.setString(2, sdthoai);
                        preparedStatement.setString(3, matkhau);
                        preparedStatement.setString(4, diachii);

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


    public void getdata_server() {
        Connectionclass connectionclass;
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();

        String query = "SELECT * FROM Employess ";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data_manager_employees.add(new Data_Manager_Employees(resultSet.getString("idemployess"),resultSet.getString("Name_employess")
                                ,resultSet.getString("Phone_Employess"),resultSet.getString("Password_employess"),resultSet.getString("Adreess_employess")));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.e("employess___", String.valueOf(data_manager_employees));
    }

//    class In extends AsyncTask<String,String,String> {
//
//
//        @Override
//        protected void onPreExecute() {
//            dialog = ProgressDialog.show(getActivity(), "Loangding", "Please wait....", true);
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            tenn.toString().trim();
//            sdthoai.toString().trim();
//            matkhau.toString().trim();
//            diachii.toString().trim();
//            Connectionclass connectionclass = new Connectionclass();
//            try
//            {
//                Connection connection = connectionclass.CONN();
//                if (connection == null) {
//                    success = false;
//                    msg = "Kết nối thất bại, kiểm tra kết nối";
//                }
//                else if (tenn.trim().equals("") || sdthoai.trim().equals("") || matkhau.trim().equals("")) {
//                    success = false;
//                    msg = "không bỏ trống các trường";
//
//                }else {
//                    String query1 = "SELECT * FROM Employess WHERE Phone_Employess= '" + sdthoai + "' " +
//                            "OR Name_employess = '" + tenn + "' ";
//                    Statement statement = connection.createStatement();
//                    ResultSet resultSet = statement.executeQuery(query1);
//                    if (resultSet.next()) {
//                        try {
//                            success = false;
//                            msg = " số điện thoại và tài khoản đã tồn tại";
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        String insertTableSQL = "INSERT INTO Employess "
//                                + "(Name_employess,Phone_Employess,Password_employess,Adreess_employess) VALUES"
//                                + "(?,?,?,?)";
//                        try {
//                            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
//                            preparedStatement.setString(1, tenn);
//                            preparedStatement.setString(2, sdthoai);
//                            preparedStatement.setString(3, matkhau);
//                            preparedStatement.setString(4, diachii);
//
//                            preparedStatement.executeUpdate();
//                            success = true;
//                            Log.e("AAAAAAAAAAAAA", String.valueOf(success));
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }catch(Exception e){
//                e.printStackTrace();
//                Writer write = new StringWriter();
//                e.printStackTrace(new PrintWriter(write));
//                msg = write.toString();
//                success = false;
//            }
//            return msg;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//                Log.e("AAAAAAAAA","DIALOG ON POST");
//            }
//            Log.e("cccccccccccccccccccc", String.valueOf(success));
//            if(success ==false) { Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show(); }
//            else { Toast.makeText(getContext(), "successfully",Toast.LENGTH_SHORT).show(); }
//
//        }
//    }


    @Override
    public void onResume() {
        super.onResume();
        data_manager_employees.clear();
        getdata_server();
        employees_manager.swapItems(data_manager_employees);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == Activity.RESULT_OK) {
                    if(data!=null){
                        tenn = data.getStringExtra("NAME_KEY");
                        Log.e(")_)))))____________",tenn);
                        matkhau = data.getStringExtra("PASS");
                        sdthoai = data.getStringExtra("PHONE_KEY");
                        diachii = data.getStringExtra("ADDRESS_KEY");
                        send_data_add();
                        if(success ==false) { Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show(); }
                        else { Toast.makeText(getContext(), "successfully",Toast.LENGTH_SHORT).show(); }
                        onResume();
                    }
                }
        }

}
