package com.example.duy26.app1.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Emplyess_details extends AppCompatActivity {
    EditText name1,sdt1,pass1,diachi1;
    ProgressDialog dialog;
    String id;
    String ten,sodt;
    String msg;
    String passs,dia;
    Boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplyess_details_mn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = this.getIntent();
        id = i.getBundleExtra("DATA").getString("ID_KEY");
        String name = i.getBundleExtra("DATA").getString("NAME_KEY");
        String sdt = i.getBundleExtra("DATA").getString("PHONE_KEY");
        String pass = i.getBundleExtra("DATA").getString("PASS_KEY");
        String diachi = i.getBundleExtra("DATA").getString("ADDRESS_KEY");

        name1 = (EditText)findViewById(R.id.fg_manager_employess_name);
        sdt1 = (EditText)findViewById(R.id.fg_manager_employess_phone);
        pass1 = (EditText)findViewById(R.id.fg_manager_employess_pass);
        diachi1 = (EditText)findViewById(R.id.fg_manager_employess_address);

        name1.setText(name);
        sdt1.setText(sdt);
        pass1.setText(pass);
        diachi1.setText(diachi);

         ten = name1.getText().toString();
         sodt = sdt1.getText().toString();
         passs = pass1.getText().toString();
         dia = diachi1.getText().toString();


        Button confirm = (Button)findViewById(R.id.fg_manager_employess_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Update update = new Update();
               update.execute("");

            }
        });
        Button cancel = (Button)findViewById(R.id.fg_manager_employess_cancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

     class Update extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(Emplyess_details.this, "Loangding", "Please wait....", true);
        }


        @Override
        protected String doInBackground(String... strings) {


            Connectionclass connectionclass = new Connectionclass();

            ten = name1.getText().toString();
            sodt = sdt1.getText().toString();
            passs = pass1.getText().toString();
            dia = diachi1.getText().toString();

            try
            {
                Connection connection = connectionclass.CONN();
                if (connection == null) {
                    success = false;
                    msg = "Kết nối thất bại, kiểm tra kết nối";
                }
                else if (ten.trim().equals("") || sodt.trim().equals("") || passs.trim().equals("")) {
                    success = false;
                    msg = "không bỏ trống các trường";

                }else {
                    String query = "SELECT * FROM [Employess] WHERE Phone_Employess= '" + sodt.toString() + "' ";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        try {
                            success = false;
                            msg = " số điện thoại tồn tại";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        try {
                String query1 = "UPDATE [Employess] SET Name_employess =?,Phone_Employess=?,Password_employess=?," +
                        "Adreess_employess=? WHERE idemployess=? ";


                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setString(1,ten);
                preparedStatement.setString(2,sodt);
                preparedStatement.setString(3,passs);
                preparedStatement.setString(4,dia);
                preparedStatement.setString(5,id);

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
            return  msg;
        }

         @Override
         protected void onPostExecute(String s) {
             if (dialog.isShowing()) {
                 dialog.dismiss();
             }
             if(success ==false) {
                 Toast.makeText(Emplyess_details.this,msg,Toast.LENGTH_LONG).show();

             }
             else
             {
                 Toast.makeText(Emplyess_details.this, "UPDATE SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                 finish();

             }
         }
     }
}

