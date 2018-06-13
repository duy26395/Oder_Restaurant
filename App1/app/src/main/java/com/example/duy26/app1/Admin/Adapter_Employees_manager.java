package com.example.duy26.app1.Admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.duy26.app1.Data;
import com.example.duy26.app1.R;

import java.util.ArrayList;

public class Adapter_Employees_manager extends RecyclerView.Adapter<Adapter_Employees_manager.viewholder> implements Onitemclick {

    public ArrayList<Data_Manager_Employees> oderdetails;
    public Context context;
    private Onitemclick onitemclick;

    public Adapter_Employees_manager(ArrayList<Data_Manager_Employees> oderdetails, Context context, Onitemclick onitemclick) {
        this.oderdetails = oderdetails;
        this.context = context;
        this.onitemclick = onitemclick;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inlater = LayoutInflater.from(parent.getContext());
        View view = inlater.inflate(R.layout.item_manager_employees, parent, false);
        final Adapter_Employees_manager.viewholder holder1 = new Adapter_Employees_manager.viewholder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclick.onItem(oderdetails.get(holder1.getAdapterPosition()),
                        holder1.getAdapterPosition());
            }
        });
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {
        holder.bindData(oderdetails.get(position));
        holder.checkBox.setOnCheckedChangeListener(null);

        holder.checkBox.setChecked(oderdetails.get(position).isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                oderdetails.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return oderdetails.size();
    }

    @Override
    public void onItem(Data_Manager_Employees data_manager_employees, int post) {

    }

    @Override
    public void click(Data data, int post) {

    }


    class viewholder extends RecyclerView.ViewHolder {

        private TextView mTvName, mIntro, dongia;
        private Onitemclick onitemclick;
        CheckBox checkBox;


        viewholder(View view) {
            super(view);

            mTvName = (TextView) view.findViewById(R.id.manager_employees_id);
            mIntro = (TextView) view.findViewById(R.id.manager_employees_idten);
            dongia = (TextView) view.findViewById(R.id.manager_employees_idsdt);
            checkBox = (CheckBox)view.findViewById(R.id.manager_employess_checkbox);



        }

        void bindData(Data_Manager_Employees data_tt) {

            mTvName.setText(data_tt.getIdnv());
            mIntro.setText(data_tt.getTen());
            dongia.setText(data_tt.getSdt());
        }

    }

    public void swapItems(ArrayList<Data_Manager_Employees> todolist){
        this.oderdetails = todolist;
        notifyDataSetChanged();
    }
}


