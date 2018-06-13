package com.example.duy26.app1.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.duy26.app1.Adapter_food;
import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.CustomItemClickListener;
import com.example.duy26.app1.Data;
import com.example.duy26.app1.Data_Food;
import com.example.duy26.app1.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Fragment_FOOD extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<Data_Food> data_food;
    ArrayList<Data_delete_food_clicklong> data_delete_food_clicklongs;
    private Button delete,add;
    private Adapter_delete_clickLong adapter_delete_clickLong;
    Connectionclass connectionclass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.fg_mn_food_recycle);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        data_food = new ArrayList<Data_Food>();
        data_delete_food_clicklongs = new ArrayList<Data_delete_food_clicklong>();
        get_dataserver();

        final Adapter_food adapter_food = new Adapter_food(data_food, getContext(), new CustomItemClickListener() {
            @Override
            public void onItemClick(Data user, int position) {

            }

            @Override
            public void onClick(Data_Food data_food, int position) {

            }
        });
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(),"AHHHIII",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        adapter_delete_clickLong = new Adapter_delete_clickLong(data_delete_food_clicklongs, getActivity(), new click_food() {
            @Override
            public void onclick(Data_delete_food_clicklong data_delete_food_clicklong, int pos) {
                Bundle bundle = new Bundle();
                bundle.putString("ID",data_delete_food_clicklong.getIdfood());
                bundle.getString("TYPE",data_delete_food_clicklong.getLoai());
                bundle.putString("NAME", data_delete_food_clicklong.getName_food());
                bundle.putString("IMAGE", data_delete_food_clicklong.getImage());
                bundle.putString("ADDRESS", data_delete_food_clicklong.getGia());
                bundle.putString("EMAIL", data_delete_food_clicklong.getDetails());
                Fragmnet_INSERT_Food_mn Fragment = new Fragmnet_INSERT_Food_mn();
                Fragment.setArguments(bundle);

                Fragment.setTargetFragment(Fragment_FOOD.this,getTargetRequestCode());
                Fragment.show(getFragmentManager(),null);

            }
        });
        recyclerView.setAdapter(adapter_delete_clickLong);
        delete = (Button)view.findViewById(R.id.manager_food_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Xoá Sản Phẩm");
                alertDialog.setMessage("Bạn có muốn xoá không?");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Data_delete_food_clicklong number : data_delete_food_clicklongs) {
                            if (number.isSelected()) {

                                Connectionclass connectionclass;
                                connectionclass = new Connectionclass();
                                Connection connection = connectionclass.CONN();

                                String query = "DELETE FROM [Food] WHERE idfood =? ";
                                try {
                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.setString(1, number.getIdfood());

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
    private void get_dataserver() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();

        try {
            String query = "SELECT idfood,NameFood,Details,Image,Prince,id_Group FROM [Food]";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data_food.add(new Data_Food(resultSet.getString("NameFood"),
                                resultSet.getString("Details"),resultSet.
                                getString("Image"),resultSet.getString("idfood")
                                ,resultSet.getString("Prince")));
                        data_delete_food_clicklongs.add(new Data_delete_food_clicklong(resultSet.getString("NameFood"),
                                resultSet.getString("Details"),resultSet.
                                getString("Image"),resultSet.getString("idfood")
                                ,resultSet.getString("Prince"),resultSet.getString("id_Group")));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch (Exception e)
        {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        data_delete_food_clicklongs.clear();
        get_dataserver();
        adapter_delete_clickLong.update_delete_food(data_delete_food_clicklongs);
    }

}
