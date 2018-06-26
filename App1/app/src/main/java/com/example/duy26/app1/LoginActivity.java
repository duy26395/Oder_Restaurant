package com.example.duy26.app1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.duy26.app1.Admin.Admin_home;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity{
    Button login,signup;
    EditText phone, password,id;
    private boolean success = false;
    private boolean adminlog = false;
    Connectionclass connectionclass;
    private ArrayList<Data_User> datalist;

    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btn_login);
        phone = (EditText) findViewById(R.id.idphone);
        password = (EditText) findViewById(R.id.idpass);

        session = new SessionManager(getApplicationContext());
        if(session.admin())
                {
                    Intent intent = new Intent(LoginActivity.this, Admin_home.class);
                    startActivityForResult(intent, 1);
                    finish();
                }

            else if (session.isLoggedIn())
            {
                // User is already logged in. Take him to main activity
                Intent intent = new Intent(LoginActivity.this, Home_Eployess.class);
                startActivityForResult(intent, 1);
                finish();
            }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checklogin checklogin = new Checklogin();
                checklogin.execute("");
            }
        });
    }

    private class Checklogin extends AsyncTask<String, String, String>{
        ProgressDialog dialog;

        String msg = "Loading....!";

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage(msg);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String usernam = phone.getText().toString();
            String passwordd = password.getText().toString();
            String ad = "admin";

            datalist = new ArrayList<Data_User>();
            try {
                if (usernam.trim().equals("") || passwordd.trim().equals("")) {
                    success = false;
                    adminlog = false;
                    msg = "không bỏ trống các trường";
                } else if(usernam.trim().equals("admin"))
                    {
                        try {
                            connectionclass = new Connectionclass();
                            Connection connection = connectionclass.CONN();
                            if (connection == null) {
                                success = false;
                                adminlog = false;
                                msg = "Kết nối thất bại, kiểm tra kết nối";
                            } else {
                                String query = "SELECT * FROM [Employess] WHERE Phone_Employess= '" + ad + "' " +
                                        "AND Password_employess = '" + passwordd.toString() + "' ";
                                Statement statement = connection.createStatement();
                                ResultSet resultSet = statement.executeQuery(query);
                                if (resultSet.next()) {
                                    try {
                                        session.admin(true);
                                        success = true;
                                        adminlog = true;
                                        msg = "Login success";


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    msg = "Tài Khoản Mật Khẩu Không Đúng";
                                    success = false;
                                }

                            }

                        } catch (Exception e) { }

                    }
                else {
                    try {
                        connectionclass = new Connectionclass();
                        Connection connection = connectionclass.CONN();
                        if (connection == null) {
                            success = false;
                            adminlog = false;
                            msg = "Kết nối thất bại, kiểm tra kết nối";
                        } else {
                            String query = "SELECT * FROM [Employess] WHERE Phone_Employess= '" + usernam.toString() + "' " +
                                    "AND Password_employess = '" + passwordd.toString() + "' ";
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery(query);
                            if (resultSet.next()) {
                                try {
                                    session.setLogin(true);
                                    datalist.add(new Data_User(resultSet.getString("idemployess"),
                                            resultSet.getString("Name_employess")));

                                    success = true;
                                    adminlog = false;
                                    msg = "Thành Công";

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                msg = "Tài Khoản Hoặc Mật Khẩu Không Đúng";
                                success = false;
                                adminlog = false;
                            }

                        }

                    } catch (Exception e) { }
                }
            }catch (Exception e) {
                e.printStackTrace();
                Writer write = new StringWriter();
                e.printStackTrace(new PrintWriter(write));
                msg = write.toString();
                success = false;
                adminlog = false;
            }
            return msg;
            }

        @Override
        protected void onPostExecute(String msg) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(success ==false && adminlog == false) { Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();}
            else {
                try {
                    if (adminlog == true && success == true)
                    {

                        Intent intent = new Intent(getApplicationContext(), Admin_home.class);
                        startActivity(intent);
                    } else if(adminlog == false && success == true){

                        Intent intent = new Intent(getApplicationContext(), Home_Eployess.class);
                        startActivity(intent);

                        SharedPreferences preferences = getSharedPreferences("ID_EMPLOYESS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("ID", datalist.get(0).getId());
                        editor.commit();

                    }

                } catch (Exception e) {

                }
            }
        }
    }


}
