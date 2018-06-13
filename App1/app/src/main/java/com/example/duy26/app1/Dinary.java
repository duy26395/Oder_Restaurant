package com.example.duy26.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Dinary extends AppCompatActivity {

    private Connectionclass connectionclass;
    private ArrayList<Data_Dinary> data_dinary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinary);

        Intent intent = this.getIntent();
        String id_user = intent.getExtras().getString("ID_USER");
        String id_diachi = intent.getExtras().getString("DIACHI");
        String name1 = intent.getExtras().getString("KEY_NAME");

        TextView name = (TextView)findViewById(R.id.dinary_name);
        TextView id_dc = (TextView)findViewById(R.id.dinary_address);

        name.setText(name1);
        id_dc.setText(id_diachi);

        int i = Integer.parseInt(id_user);

        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();

        data_dinary = new ArrayList<Data_Dinary>();
        String query = "SELECT idbill,date_bill,idemployess,iduser FROM [Bill] Where iduser = '" + i + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data_dinary.add(new Data_Dinary(resultSet.getInt("idbill"),resultSet.getString("date_bill"),
                                resultSet.getInt("idemployess"),resultSet.getInt("iduser")));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.e("_", String.valueOf(data_dinary));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dinary_recycle);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Adapter_dinary adapter_dinary = new Adapter_dinary(data_dinary, Dinary.this, new Onitemclick123() {
            @Override
            public void onItem(Data_Dinary data_dinary, int post) {
                Bundle bundle = new Bundle();
                bundle.putInt("ID_BILL",data_dinary.getId_bill());
                FullscreenDialogFragment newFragment = new FullscreenDialogFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(),null);
            }
        });
        recyclerView.setAdapter(adapter_dinary);

    }


}
