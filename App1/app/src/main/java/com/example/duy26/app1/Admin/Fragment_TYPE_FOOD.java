package com.example.duy26.app1.Admin;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

public class Fragment_TYPE_FOOD extends Fragment {
    RecyclerView recyclerView;
    Connectionclass connectionclass;
    Adapter_food_type adapter_food_type;
    private Button delete,add;
    ArrayList<Data_food_type> data_food_types;
    private String tenn,msg,insert_name,update_name;
    private boolean success = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_type_mn, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.manager_food_type_reycycle);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Log.e("LOG_DB_FOOOD_TYPE", String.valueOf(data_food_types));
        data_food_types = new ArrayList<Data_food_type>();
        gdata_server();
        adapter_food_type = new Adapter_food_type(data_food_types, getActivity(), new Click_food_type() {
            @Override
            public void click_food_type(Data_food_type data_food_type, int postion) {

                Bundle bundle = new Bundle();
                bundle.putString("NAME_FOOD", data_food_type.getName());
                bundle.putInt("ID_FOOD",data_food_type.getId_tpye());
                Fragment_ins_foodtype Fragment = new Fragment_ins_foodtype();
                Fragment.setArguments(bundle);

                Fragment.setTargetFragment(Fragment_TYPE_FOOD.this,getTargetRequestCode());
                Fragment.show(getFragmentManager(),null);

            }
        });
        recyclerView.setAdapter(adapter_food_type);
        delete = (Button)view.findViewById(R.id.manager_food_type_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            int i =0;
            @Override
            public void onClick(View v) {
                Data_food_type data_food_type;
                for (Data_food_type number : data_food_types) {
                    if (number.isSelected()) {
                        i++;
                        Log.e("COUNT ________NUMBER",Integer.toString(i));
                    }
                }
                if (i == 1) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Xoá Loại Sản Phẩm");
                    alertDialog.setMessage("Bạn phải xoá toàn bộ sản phẩm thuộc nhóm này !! ");
                    alertDialog.setCancelable(false);
                    alertDialog.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            for (Data_food_type number : data_food_types) {
                                if (number.isSelected()) {
                                    Fragment_delete_idFOOD Fragment = new Fragment_delete_idFOOD();
                                    int id_group = number.getId_tpye();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("ID_GROUP", id_group);
                                    Fragment.setArguments(bundle);
                                    FragmentManager fragmentManager = getChildFragmentManager();
                                    Fragment.show(fragmentManager, null);
                                }
                            }
                            for (Data_food_type number : data_food_types) {
                                if (number.isSelected()) {
                                    Connectionclass connectionclass;
                                    connectionclass = new Connectionclass();
                                    Connection connection = connectionclass.CONN();

                                    String query = "DELETE FROM Group_Food WHERE id_Group=? ";
                                    try {
                                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                                        preparedStatement.setInt(1, number.getId_tpye());
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
                } else {

                    Toast.makeText(getActivity(),"Chọn 1 loại sản phẩn để xoá",Toast.LENGTH_SHORT).show();
                    onDestroyView();
                }
                i=0;
            }
        });
        add = (Button) view.findViewById(R.id.manager_food_type_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food_TYPE_add Fragment = new Food_TYPE_add();
                Fragment.setTargetFragment(Fragment_TYPE_FOOD.this,getTargetRequestCode());
                Fragment.show(getFragmentManager(),null);
            }
        });

        return view;

    }
    private void update_server(){
        update_name.toString().trim();
        int i = Integer.parseInt(insert_name);
        Connectionclass connectionclass = new Connectionclass();
        try
        {
            Connection connection = connectionclass.CONN();
            if (connection == null) {
                success = false;
                msg = "Kết nối thất bại, kiểm tra kết nối";
            }
            else if (update_name.trim().equals("")) {
                success = false;
                msg = "không bỏ trống các trường";

            }else {
                String query_insert_cus = "SELECT * FROM [Group_Food] WHERE Name_group = '" + update_name + "' " ;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query_insert_cus);
                if (resultSet.next()) {
                    try {
                        success = false;
                        msg = " Tên đã tồn tại";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String query1 = "UPDATE [Group_Food] SET Name_group =? WHERE id_Group =?";

                        PreparedStatement preparedStatement = connection.prepareStatement(query1);
                        preparedStatement.setString(1,update_name);
                        preparedStatement.setInt(2,i);

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
    private void gdata_server() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();
        try {
            String query5 = "SELECT id_Group,Name_group FROM [Group_Food]";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query5);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data_food_types.add(new Data_food_type(resultSet.getInt("id_Group"),
                                resultSet.getString("Name_group")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch (Exception e)
        {

        }
        Log.e("LOG_DB_FOOOD_TYPE", String.valueOf(data_food_types));
    }
    private void send_data_add() {
        tenn.toString().trim();

        Connectionclass connectionclass = new Connectionclass();
        try
        {
            Connection connection = connectionclass.CONN();
            if (connection == null) {
                success = false;
                msg = "Kết nối thất bại, kiểm tra kết nối";
            }
            else if (tenn.trim().equals("")) {
                success = false;
                msg = "không bỏ trống các trường";

            }else {
                String query1 = "SELECT * FROM Group_Food WHERE Name_group= '" + tenn + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query1);
                if (resultSet.next()) {
                    try {
                        success = false;
                        msg = "  tên tồn tại ";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String insertTableSQL = "INSERT INTO Group_Food "
                            + "(Name_group) VALUES"
                            + "(?)";
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
                        preparedStatement.setString(1, tenn);

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
                tenn = data.getStringExtra("NAME_ADD");
                send_data_add();

                if (success == false) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "successfully", Toast.LENGTH_SHORT).show();
                }
                onResume();
            }
        }else {
                if (data != null) {
                    update_name = data.getStringExtra("NAME_KEY_FOOD");
                    insert_name = data.getStringExtra("ID_KEY");
                    Log.e("AAAAAAAAAAAAA",update_name);
                    update_server();
                    if (success == false) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();

                    }
                    onResume();
                }
            }
        }

    @Override
    public void onResume() {
        super.onResume();
        data_food_types.clear();
        gdata_server();
        adapter_food_type.update_Bill(data_food_types);
    }
}
