package com.example.duy26.app1.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.duy26.app1.Connectionclass;
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

public class Fragment_delete_idFOOD extends DialogFragment {
    RecyclerView recyclerView;
    Connectionclass connectionclass;
    private String msg;
    private int id_gr;
    private Button delete;
    private Boolean success = false;
    Adapter_delete_IDfood adapter_delete_iDfood;
    private ArrayList<Data_Food_delete> data_food_types;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_id_food, container, false);
        id_gr = getArguments().getInt("ID_GROUP");
        recyclerView = (RecyclerView) view.findViewById(R.id.id_recycle_delete_id_food);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        data_food_types = new ArrayList<Data_Food_delete>();
        getdata_Server();
        adapter_delete_iDfood = new Adapter_delete_IDfood(data_food_types,getActivity());
        recyclerView.setAdapter(adapter_delete_iDfood);
        delete = (Button)view.findViewById(R.id.fg_dlelet_confirm);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Xoá Sản Phẩm");
                alertDialog.setMessage("Bạn muốn xoá sản phẩm này !! ");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Data_Food_delete number : data_food_types) {
                            if (number.isSelected()) {
                                Connectionclass connectionclass;
                                connectionclass = new Connectionclass();
                                Connection connection = connectionclass.CONN();

                                String query = "DELETE FROM Food WHERE idfood=? ";
                                try {
                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.setString(1, number.getId_foood());
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
        (view.findViewById(R.id.button_close_idfood)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroyView();
            }
        });

        return view;

    }

    private void getdata_Server() {
        try {
            connectionclass = new Connectionclass();
            Connection connection = connectionclass.CONN();
            if (connection == null) {
                success = false;
            } else {
                String query = "SELECT idfood,NameFood,Prince FROM [FOOD] WHERE id_Group = '" + id_gr + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        try {
                            data_food_types.add(new Data_Food_delete(resultSet.getString("idfood"), resultSet.getString("NameFood"),
                                    resultSet.getString("Prince")));

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

    @Override
    public void onResume() {
        super.onResume();
        data_food_types.clear();
        getdata_Server();
        adapter_delete_iDfood.update_delete_food(data_food_types);
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
        Dialog dialogFragment  = getDialog();
        if (dialogFragment != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialogFragment.getWindow().setLayout(width, height);
        }
    }
}
