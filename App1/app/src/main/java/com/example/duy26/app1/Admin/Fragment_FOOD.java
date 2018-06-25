package com.example.duy26.app1.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.duy26.app1.Adapter_food;
import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.CustomItemClickListener;
import com.example.duy26.app1.Data;
import com.example.duy26.app1.Data_Food;
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

public class Fragment_FOOD extends Fragment {
    public static final int REQUEST_UPDATE = 2;
    public static final  int REQUEST_CODE_THIS = 3;
    private RecyclerView recyclerView;
    ArrayList<Data_Food> data_food;
    ArrayList<Data_delete_food_clicklong> data_delete_food_clicklongs;
    private Button delete,add;
    private Adapter_delete_clickLong adapter_delete_clickLong;
    Connectionclass connectionclass;

    private String id_f,name_f,price_f,details_f,id_gr,img_f,msg;
    private Boolean success = false;
    private ImageView imagebox;
    private String addname,addprice,adddetails,addimg,addidgr,addid;

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
                bundle.putString("NAME_TYPE", data_delete_food_clicklong.getName_type());

                Fragmnet_INSERT_Food_mn Fragment = new Fragmnet_INSERT_Food_mn();
                Fragment.setArguments(bundle);

                Fragment.setTargetFragment(Fragment_FOOD.this,REQUEST_UPDATE);
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

        Button food_add = (Button)view.findViewById(R.id.manager_food_add);
        food_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Food_Add Fragment = new Fragment_Food_Add();
                Fragment.setTargetFragment(Fragment_FOOD.this,REQUEST_CODE_THIS);
                Fragment.show(getFragmentManager(),null);
            }
        });

        return view;
    }
    private void get_dataserver() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();

        try {
            String query = "SELECT [idfood]\n" +
                    "      ,[NameFood]\n" +
                    "      ,[Details]\n" +
                    "      ,[Image]\n" +
                    "      ,[Prince]\n" +
                    "      ,Food .id_Group \n" +
                    "      ,Name_group\n" +
                    "  FROM [Food]  JOIN Group_Food \n" +
                    "    ON [Food] .id_Group  = Group_Food .id_Group";
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
                                ,resultSet.getString("Prince"),resultSet.getString("id_Group"),resultSet.getString("Name_group")));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch (Exception e)
        {

        }
    }
    private void send_data_insert(){

        name_f.trim();
        details_f.trim();
        img_f.trim();
        price_f.trim();
        id_f.trim();
        id_gr.trim();

        try
        {
            Connection connection = connectionclass.CONN();
            if (connection == null) {
                success = false;
                msg = "Kết nối thất bại, kiểm tra kết nối";
            }
            else if (name_f.trim().equals("") || price_f.trim().equals("")) {
                success = false;
                msg = "không bỏ trống các trường";

            }else {
                String query = "SELECT * FROM [Food] WHERE NameFood= N'" + name_f.toString().trim() + "' ";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    try {
                        success = false;
                        msg = " Tên đã tồn tại";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String query1 = "UPDATE [Food] SET NameFood =?,Prince=?,Details=?,Image=?,id_Group=? WHERE idfood =? ";

                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(query1);
                        preparedStatement.setString(1, name_f);
                        preparedStatement.setString(2, price_f);
                        preparedStatement.setString(3, details_f);
                        preparedStatement.setString(4, img_f);
                        preparedStatement.setString(5,id_gr);
                        preparedStatement.setString(6,id_f);

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
    private void send_data_add(){
        addimg.trim();
        try
        {
            Connection connection = connectionclass.CONN();
            if (connection == null) {
                success = false;
                msg = "Kết nối thất bại, kiểm tra kết nối";
            }
            else if (addname.trim().equals("") || addprice.trim().equals("")|| addidgr.trim().equals("") ) {
                success = false;
                msg = "không bỏ trống các trường";

            }else {
                String query = "SELECT * FROM [Food] WHERE idfood= '" + addid.toString() + "' ";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    try {
                        success = false;
                        msg = " ID_KEY đã tồn tại";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String insertTableSQL = "INSERT INTO [Food] "
                            + "(idfood,NameFood,Details,Image,id_Group,Prince) VALUES"
                            + "(?,?,?,?,?,?)";
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
                        preparedStatement.setString(1, addid);
                        preparedStatement.setString(2, addname);
                        preparedStatement.setString(3, adddetails);
                        preparedStatement.setString(4, addimg);
                        preparedStatement.setString(5, addidgr);
                        preparedStatement.setString(6, addprice);

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
    public void onResume() {
        super.onResume();
        data_delete_food_clicklongs.clear();
        get_dataserver();
        adapter_delete_clickLong.update_delete_food(data_delete_food_clicklongs);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
           switch(requestCode) {
               case REQUEST_UPDATE :
                id_f = data.getStringExtra("ID_KEY");
                name_f = data.getStringExtra("NAME_FOOD");
                price_f = data.getStringExtra("PRICE_FOOD");
                details_f = data.getStringExtra("MOTA_FOOD");
                id_gr = data.getStringExtra("ID_GROUP");
                img_f = data.getStringExtra("IMG_F");
                send_data_insert();
                if (success == false) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show(); }
                    else {
                    Toast.makeText(getContext(), "UPDATE Successfully", Toast.LENGTH_LONG).show(); }
                onResume();
            case REQUEST_CODE_THIS :
                    addname = data.getStringExtra("NAME_AF");
                    adddetails= data.getStringExtra("DETAILS_AF");
                    addidgr = data.getStringExtra("IDGR_AF");
                    addimg = "";
                    addimg = data.getStringExtra("IMG_AF");
                    addprice = data.getStringExtra("PRICE_AF");
                    addid = data.getStringExtra("ID_AF");
                    send_data_add();
                    if (success == false) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "INSERT Successfully", Toast.LENGTH_LONG).show();
                    }
                    onResume();
           }
        }
    }



}
