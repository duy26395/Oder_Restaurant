package com.example.duy26.app1;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FullscreenDialogFragment extends DialogFragment {
    private Connectionclass connectionclass;
    private ArrayList<Data_billdetails_dinary> data_dinary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_screen_dialog, container, false);
        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroyView();
            }
        });
        getdata();
        Log.e("IIAIAIAIAIAIAGGGGGGGG", String.valueOf(data_dinary));
        if(data_dinary.size() == 0 )
        {
            Toast.makeText(getContext(),"Không có dữ liệu",Toast.LENGTH_LONG).show();


        } else {
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.id_recycle_dinarrydetails);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            adapter_dianry_details adapter_dinary = new adapter_dianry_details(data_dinary, getActivity());
            recyclerView.setAdapter(adapter_dinary);
        }
        return rootView;
    }

    private void getdata() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();

        int i = getArguments().getInt("ID_BILL");
        data_dinary = new ArrayList<Data_billdetails_dinary>();

        String query = "SELECT Food.NameFood,Bill_details.Number,Food.Prince FROM " +
                "Food JOIN Bill_details ON Food.idfood = Bill_details.idfood Where" +
                " Bill_details.idbill = '" + i + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data_dinary.add(new Data_billdetails_dinary(resultSet.getString("NameFood"),
                                resultSet.getString("Number"), resultSet.getString("Price")));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        Dialog dialogFragment  = getDialog();
        if (dialogFragment != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialogFragment.getWindow().setLayout(width, height);
        }
    }
}