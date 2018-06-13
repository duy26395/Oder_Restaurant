package com.example.duy26.app1.Admin;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.duy26.app1.R;

public class Fragment_customer_insert extends DialogFragment {
    private  EditText ten,sdt,diachi,mail;
    String id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_insert_mn, container, false);

        String name = getArguments().getString("NAME");
        String phone = getArguments().getString("PHONE");
        String address = getArguments().getString("ADDRESS");
        String email = getArguments().getString("EMAIL");
        id = getArguments().getString("ID");

        ten = (EditText)view.findViewById(R.id.fg_insert_customer_name);
        sdt = (EditText)view.findViewById(R.id.fg_insert_customer_phone);
        mail = (EditText)view.findViewById(R.id.fg_insert_customer_email);
        diachi = (EditText)view.findViewById(R.id.fg_insert_customer_address);

        ten.setText(name);
        sdt.setText(phone);
        mail.setText(email);
        diachi.setText(address);

        Button cofirm = (Button)view.findViewById(R.id.fg_insert_customer_confirm);
        cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten1 = ten.getText().toString();
                String sodt = sdt.getText().toString();
                String maill = mail.getText().toString();
                String dia = diachi.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("ID_KEY",id);
                intent.putExtra("NAME_KEY", ten1);
                intent.putExtra("PHONE_KEY", sodt);
                intent.putExtra("ADDRESS_KEY", dia);
                intent.putExtra("MAIL_KEY",maill );

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);

            }
        });
        Button close = (Button)view.findViewById(R.id.fg_insert_customer_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialogFragment.getWindow().setLayout(width, height);
        }
    }

}
