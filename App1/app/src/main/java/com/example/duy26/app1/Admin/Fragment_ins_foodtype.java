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
import android.widget.Button;
import android.widget.EditText;

import com.example.duy26.app1.R;

public class Fragment_ins_foodtype extends DialogFragment {
    public static final int REQUEST = 232;
    EditText ten;
    private  String idd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_type_insert, container, false);

        String name = getArguments().getString("NAME_FOOD");
        final int id = getArguments().getInt("ID_FOOD");
        ten = (EditText)view.findViewById(R.id.fg_ins_food_type_name);
        ten.setText(name);

        idd = String.valueOf(id);
        Button cofirm = (Button)view.findViewById(R.id.fg_ins_foodtype_confirm);
        cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten1 = ten.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("NAME_KEY_FOOD", ten1);
                intent.putExtra("ID_KEY",idd);
                Log.e("AAAAAAAAAAAAA", idd);

                getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST,intent);

            }
        });
        Button close = (Button)view.findViewById(R.id.fg_ins_foodtype_close);
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
