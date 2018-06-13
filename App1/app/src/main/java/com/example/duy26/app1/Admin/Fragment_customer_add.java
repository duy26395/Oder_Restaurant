package com.example.duy26.app1.Admin;

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

public class Fragment_customer_add extends DialogFragment {
    private EditText ten,sdt,mail,diachi;
    private static final int REQUEST_CODE_THIS = 5;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_add_mn, container, false);

        Button cancle = (Button)view.findViewById(R.id.fg_add_customer_close);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ten = (EditText)view.findViewById(R.id.fg_add_customer_name);
        sdt = (EditText)view.findViewById(R.id.fg_add_customer_phone);
        mail = (EditText)view.findViewById(R.id.fg_add_customer_email);
        diachi = (EditText)view.findViewById(R.id.fg_add_customer_address);

        Button cofirm = (Button)view.findViewById(R.id.fg_add_customer_confirm);
        cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten1 = ten.getText().toString();
                String sodt = sdt.getText().toString();
                String email = mail.getText().toString();
                String dia = diachi.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("NAME_ADD", ten1);
                intent.putExtra("PHONE_ADD", sodt);
                intent.putExtra("ADDRESS_ADD", dia);
                intent.putExtra("MAIL_ADD", email);

                getTargetFragment().onActivityResult(getTargetRequestCode(),REQUEST_CODE_THIS ,intent);

                ten.setText("");
                sdt.setText("");
                mail.setText("");
                diachi.setText("");

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
