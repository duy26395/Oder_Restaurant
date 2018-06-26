package com.example.duy26.app1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duy26.app1.Admin.Admin_home;
import com.example.duy26.app1.Admin.Bill_ofDetails_Insert_interface;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.content.Context.MODE_PRIVATE;

public class Dialog_fragment_oder extends DialogFragment {
    TextView name1,gia1;
    private Context context;
    EditText soluong,ghichu;
    SQLiteHander sqLiteHander;
    SessionManager session;
    private String id_bill,id_employess,id_food,dongia,ten;
    private String s1;
    private Bill_ofDetails_Insert_interface insertInterface;
    Connectionclass connectionclass;
    private Boolean success = false;
    private String msg = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_oderrbill, container, false);


        ten = getArguments().getString("NAME_KEY");
        id_food= getArguments().getString("ID_KEY");
        dongia = getArguments().getString("PRINCE");
        id_bill = getArguments().getString("ID_BILL");

        name1 = (TextView)view.findViewById(R.id.id_fragmentenoder);
        gia1 = (TextView)view.findViewById(R.id.id_fragmentprince);
        soluong = (EditText)view.findViewById(R.id.id_fragmentnumber);
        ghichu = (EditText)view.findViewById(R.id.id_fragmentghichu);

        name1.setText(ten);
        gia1.setText(dongia);

        Button btn_confirm = (Button)view.findViewById(R.id.id_fragbtn_ok);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button btn_cancle = (Button)view.findViewById(R.id.id_fragbtn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        session = new SessionManager(getActivity());

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(session.admin())
                {
                    send_data_mn();
                    getDialog().dismiss();

                    Toast.makeText(getActivity(),"Successfully",Toast.LENGTH_LONG).show();
                }

                else if (session.isLoggedIn())
                {
                    sendata();
                    getDialog().dismiss();
                }


            }
        });

        return view;

    }

    //pass data fragment to activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            insertInterface = (Bill_ofDetails_Insert_interface) activity;
        }
        catch (ClassCastException e) {
            Log.d("MyDialog", "Activity doesn't implement the ISelectedData interface");
        }
    }

    private void send_data_mn()  {
        s1 = soluong.getText().toString().trim();
        String note = ghichu.getText().toString().trim();
        insertInterface.result(id_food,Integer.parseInt(s1));

    }

    private void sendata() {
        String sl;
        sl = soluong.getText().toString().trim();
        String note = ghichu.getText().toString().trim();

        sqLiteHander = new SQLiteHander(getContext());
        Data_oderdetails data_oderdetails = new Data_oderdetails();

                data_oderdetails.setFood(ten);
                data_oderdetails.setId_food(id_food);
                data_oderdetails.setNumber_details(sl);
                data_oderdetails.setPrince(dongia);
                data_oderdetails.setId_bill(id_bill);

                sqLiteHander.addTEAM(data_oderdetails);

                Log.e("ADD BILL_________________", String.valueOf(data_oderdetails));
        Toast.makeText(getContext(),"Thành Công",Toast.LENGTH_SHORT).show();
                soluong.setText("1");
                ghichu.setText("");
    }

}
