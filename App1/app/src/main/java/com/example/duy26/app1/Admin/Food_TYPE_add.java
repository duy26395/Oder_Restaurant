package com.example.duy26.app1.Admin;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.duy26.app1.R;

public class Food_TYPE_add extends DialogFragment {
    private static final int REQUEST_CODE_THIS = 12;
    private EditText ten;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_type_add, container, false);

        Button cancle = (Button)view.findViewById(R.id.fg_add_foodtype_close);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ten = (EditText)view.findViewById(R.id.fg_add_foodtype_name);
        Button cofirm = (Button)view.findViewById(R.id.fg_add_foodtype_confirm);
        cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten1 = ten.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("NAME_ADD", ten1);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);

                ten.setText("");
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
