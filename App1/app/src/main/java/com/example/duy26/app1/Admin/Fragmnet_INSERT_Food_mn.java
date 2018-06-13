package com.example.duy26.app1.Admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Fragmnet_INSERT_Food_mn extends DialogFragment {
    private Connectionclass connectionclass;
    private String id;
    private String type_id;
    private String mTvCountry;
    private String name_group;
    private AdapterView adapterView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> data_food_types;
    private EditText ten, gia, mota, hinhanh;
    private MaterialBetterSpinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_food_mn, container, false);


        String name_f = getArguments().getString("NAME");
        type_id = getArguments().getString("TYPE");
        String address_f = getArguments().getString("ADDRESS");
        String email_f = getArguments().getString("EMAIL");
        String img = getArguments().getString("IMAGE");
        id = getArguments().getString("ID");

        (view.findViewById(R.id.food_ins_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ten = (EditText) view.findViewById(R.id.food_ins_name);
        gia = (EditText) view.findViewById(R.id.food_ins_price);
        mota = (EditText) view.findViewById(R.id.food_ins_details);
        hinhanh = (EditText) view.findViewById(R.id.food_ins_img);

        ten.setText(name_f);
        gia.setText(address_f);
        mota.setText(email_f);
        hinhanh.setText(img);

        data_food_types = new ArrayList<String>();
        gdata_server_type_food();
        name_group = new String();
        get_defaul_name();

        spinner = (MaterialBetterSpinner) view.findViewById(R.id.food_ins_type);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, data_food_types);
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                mTvCountry = (String) parent.getItemAtPosition(position);

            }
        });
        Button cofirm = (Button) view.findViewById(R.id.food_ins_confirm);
        cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getActivity(), mTvCountry, Toast.LENGTH_SHORT).show();
            }
        });
        Button search_img = (Button)view.findViewById(R.id.search_img);
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), 1);
            }
        });


        return view;
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
            dialogFragment.getWindow().setLayout(width, height);
        }
    }

    private void gdata_server_type_food() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();
        try {
            String query5 = "SELECT id_Group,Name_group FROM [Group_Food]";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query5);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data_food_types.add(resultSet.getString("Name_group"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
        }
        Log.e("LOG_DB_FOOOD_TYPE", String.valueOf(data_food_types));
    }
    private void get_defaul_name()
    {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();
        try {
            String query5 = "SELECT id_Group FROM [Group_Food] WHERE id_Group = '"+ type_id +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query5);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        name_group = resultSet.getString("Name_group");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch (Exception e) {        }
    }
}