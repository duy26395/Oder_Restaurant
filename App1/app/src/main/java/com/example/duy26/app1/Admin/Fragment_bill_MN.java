package com.example.duy26.app1.Admin;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.LoginActivity;
import com.example.duy26.app1.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_bill_MN extends Fragment {
    RecyclerView recyclerView;
    Connectionclass connectionclass;
    Adapter_bill_mn adapter_bill_mn;
    private Button delete;
    private ProgressBar progressBar;
    private ArrayList<Data_mn_BILL_details> data;
    private String id_delete_bill;
    private int i =0;
    private Boolean check = false;


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

        delete = (Button) view.findViewById(R.id.bill_mn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Data_mn_BILL_details number : data) {
                    if (number.isSelected()) { i++; }
                    }
                    if (i == 1) {
//                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//                    alertDialog.setTitle("Xoá Hoá Đơn");
//                    alertDialog.setMessage("Bạn muốn xoá toàn bộ hoá đơn thuộc nhóm này !! ");
//                    alertDialog.setCancelable(false);
//                    alertDialog.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            try{
//                                for (Data_mn_BILL_details number : data) {
//                                    if (number.isSelected()) {
//                                        connectionclass = new Connectionclass();
//                                        Connection connection = connectionclass.CONN();
//
//                                        String query = "DELETE FROM [Bill] WHERE idbill =? ";
//                                        try {
//                                            PreparedStatement preparedStatement = connection.prepareStatement(query);
//                                            preparedStatement.setInt(1, number.getIdbill());
//
//                                            preparedStatement.executeUpdate();
//
//                                        } catch (SQLException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
//                            } catch (Exception e)
//                            { AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setMessage("Chọn hoá đơn và xoá toàn bộ hoá đơn của sản phẩm ");}
                        for (Data_mn_BILL_details number : data) {
                            if (number.isSelected()) {
                                id_delete_bill = String.valueOf(number.getIdbill());
                                }
                        }
                        Delete_bill delete_bill = new Delete_bill();
                        delete_bill.execute("");
//
//                            onResume();
//                            dialog.dismiss();
                    } else {
                    Toast.makeText(getActivity(),"Chọn 1 để thực hiện",Toast.LENGTH_SHORT).show();
                    onDestroyView();
                }

                    i=0;
            }
        });
//                    });
//                    alertDialog.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    AlertDialog alert = alertDialog.create();
//                    alert.show();
//                } else {
//
//                    Toast.makeText(getActivity(),"Chọn 1 để xoá",Toast.LENGTH_SHORT).show();
//                    onDestroyView();
//                }
//                i=0;
//            }
//        });
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//                alertDialog.setTitle("Xoá Hoá Đơn");
//                alertDialog.setMessage("Bạn có muốn xoá không?");
//                alertDialog.setCancelable(false);
//                alertDialog.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        for (Data_mn_BILL_details number : data) {
//                            if (number.isSelected()) {
//
//                                Connectionclass connectionclass;
//                                connectionclass = new Connectionclass();
//                                Connection connection = connectionclass.CONN();
//
//                                String query = "DELETE FROM [Bill] WHERE idbill =? ";
//                                try {
//                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
//                                    preparedStatement.setInt(1, number.getIdbill());
//
//                                    preparedStatement.executeUpdate();
//
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        onResume();
//                        dialog.dismiss();
//                    }
//                });
//                alertDialog.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                AlertDialog alert = alertDialog.create();
//                alert.show();
//            }
//        });
        adapter_bill_mn = new Adapter_bill_mn(data, getContext(), new Click_data_bill_details() {

            @Override
            public void ItemClick(Data_mn_BILL_details user, int position) {

                id_delete_bill = String.valueOf(user.getIdbill());
                check_id();
                if (check == true){
                    Intent intent = new Intent(getActivity(), Manager_Bill_Details.class);
                    intent.putExtra("ID",Integer.toString(user.getIdbill()));
                    startActivity(intent);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Hoá Đơn "+id_delete_bill);
                    builder.setMessage("Hoá đơn trống");
                    final AlertDialog alert = builder.create();
                    alert.show();
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            timer.cancel();
                        }
                    },3000);// 2s
                }


            }
        });
        recyclerView.setAdapter(adapter_bill_mn);


        return view;
    }

    private void check_id() {
            connectionclass = new Connectionclass();
            Connection connection = connectionclass.CONN();
            try {
                String query = "SELECT * FROM [Bill_details] WHERE idbill= '" + Integer.parseInt(id_delete_bill) + "'";
                Statement preparedStatement = connection.createStatement();
                ResultSet resultSet = preparedStatement.executeQuery(query);
                if (resultSet.next()) {
                    try {
                        check = true;
                    } catch (Exception e) {
                    }
                } else {
                    check = false;
                }
            } catch (Exception e) { }


    }

    private void getdata_Server() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();

        data = new ArrayList<Data_mn_BILL_details>();
        String query22 = "SELECT [Bill].idbill,\n" +
                "[Bill].date_bill,\n" +
                "[Employess].Name_employess,\n" +
                "[User].Name \n" +
                "FROM [Bill] JOIN [User] ON Bill.iduser = [User].iduser\n" +
                " JOIN [Employess] ON Bill .idemployess   = Employess.idemployess";
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

    private class Delete_bill extends AsyncTask<String,String,String> {

        ProgressDialog dialog123;
        AlertDialog.Builder alertDialog;
        String msg = "";


        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(getActivity());
            dialog123 = ProgressDialog.show(getContext(),"Loading", "Please wait....", true);
        }

        @Override
        protected String doInBackground(String... strings) {

            check_id();
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            alertDialog.setTitle("Xoá Hoá Đơn");
            alertDialog.setMessage("Bạn muốn xoá toàn bộ hoá đơn thuộc nhóm này !! ");
            alertDialog.setCancelable(false);
            alertDialog.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (check == true) {
                        Intent intent = new Intent(getActivity(), Manager_Bill_Details.class);
                        intent.putExtra("ID", id_delete_bill);
                        startActivity(intent);
                        dialog123.dismiss();
                    } else {
                        delete_of_null();
                        Toast.makeText(getActivity(), "DELETE SUCCESSFULLY", Toast.LENGTH_LONG).show();
                        dialog123.dismiss();
                    }
                    onResume();

                    dialog.dismiss();
                }

            });
            alertDialog.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dialog123.dismiss();
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.show();

        }

    }

    private void delete_of_null() {
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
    }

}
