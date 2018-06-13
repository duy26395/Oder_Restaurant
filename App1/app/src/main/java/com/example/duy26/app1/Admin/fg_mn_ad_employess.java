package com.example.duy26.app1.Admin;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
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

public class fg_mn_ad_employess extends DialogFragment {
    private  EditText ten,sdt,mk,diachi;
    ProgressDialog dialog;
    boolean success = false;
    String msg = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mn_employess, container, false);
        Button cancle = (Button)view.findViewById(R.id.fg_add_employess_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ten = (EditText)view.findViewById(R.id.fg_add_employess_name);
        sdt = (EditText)view.findViewById(R.id.fg_add_employess_phone);
        mk = (EditText)view.findViewById(R.id.fg_manager_add_employess_pass);
        diachi = (EditText)view.findViewById(R.id.fg_add_employess_address);

        Button cofirm = (Button)view.findViewById(R.id.fg_add_employess_confirm);
        cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten1 = ten.getText().toString();
                String sodt = sdt.getText().toString();
                String passs = mk.getText().toString();
                String dia = diachi.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("NAME_KEY", ten1);
                intent.putExtra("PHONE_KEY", sodt);
                intent.putExtra("ADDRESS_KEY", dia);
                intent.putExtra("PASS", passs);

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);

                ten.setText("");
                sdt.setText("");
                mk.setText("");
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
