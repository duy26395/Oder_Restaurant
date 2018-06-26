package com.example.duy26.app1.Admin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.CustomItemClickListener;
import com.example.duy26.app1.Data;
import com.example.duy26.app1.Data_Food;
import com.example.duy26.app1.Dialog_fragment_oder;
import com.example.duy26.app1.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Bill_ofDetails_ADD extends android.support.v4.app.DialogFragment implements Bill_ofDetails_Insert_interface{
    RecyclerView recyclerView;
    private ArrayList<Data_Food> data_food;
    Connectionclass connectionclass;
    Adapter_BillofDetails_add adapter_food;
    private String id_bill,id_add;
    private int sott;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_ofdetails_add, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.bill_ofDetails_recycle);
        recyclerView.setHasFixedSize(true);
        id_bill = getArguments().getString("ID_BILL");

        view.findViewById(R.id.bill_ofDetails_button_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                onDestroyView();

            }
        });

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        data_food = new ArrayList<Data_Food>();
        SyncData orderData = new SyncData();
        orderData.execute("");
        EditText editText = view.findViewById(R.id.bill_ofDetails_search);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        adapter_food.getFilter().filter(s);
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
        Dialog dialogFragment  = getDialog();
        if (dialogFragment != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialogFragment.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void result(String id_food, int number) {
        sott = number;
        id_add = id_food;
    }

    private class SyncData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            connectionclass = new Connectionclass();
            Connection connection = connectionclass.CONN();

            try {
                String query = "SELECT idfood,NameFood,Details,Image,Prince FROM [Food]";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        try {
                            data_food.add(new Data_Food(resultSet.getString("NameFood"),
                                    resultSet.getString("Details"),resultSet.
                                    getString("Image"),resultSet.getString("idfood")
                                    ,resultSet.getString("Prince")));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }catch (Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
             adapter_food = new Adapter_BillofDetails_add(data_food, getActivity(), new CustomItemClickListener() {
                @Override
                public void onItemClick(Data user, int position) {

                }
                @Override
                public void onClick(Data_Food data_food, int position) {
                    Toast.makeText(getActivity(),""+data_food.getName_food(),Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("ID_KEY", data_food.getIdfood());
                    bundle.putString("PRINCE",data_food.getGia());
                    bundle.putString("NAME_KEY",data_food.getName_food());
                    bundle.putString("ID_BILL",id_bill);
                    Dialog_fragment_oder fragment_oder = new Dialog_fragment_oder();
                    fragment_oder.setArguments(bundle);
                    fragment_oder.show(getFragmentManager(),null);


                }

            });
            recyclerView.setAdapter(adapter_food);
        }

    }

}
