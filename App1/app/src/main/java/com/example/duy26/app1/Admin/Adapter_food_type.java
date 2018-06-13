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

import com.example.duy26.app1.R;

import java.util.ArrayList;

public class Adapter_food_type extends RecyclerView.Adapter<Adapter_food_type.ViewHolder> implements Click_food_type {
    private ArrayList<Data_food_type> data_dinaries;
    private Context context;
    private Click_food_type onItemClick;

    public Adapter_food_type(ArrayList<Data_food_type> data_dinaries, Context context, Click_food_type onItemClick) {
        this.data_dinaries = data_dinaries;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public Adapter_food_type.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_type, parent, false);
        final Adapter_food_type.ViewHolder holder1 = new Adapter_food_type.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.click_food_type(data_dinaries.get(holder1.getAdapterPosition()),
                        holder1.getAdapterPosition());
            }
        });
        return holder1;

    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_food_type.ViewHolder holder, int position) {
        holder.bindData(data_dinaries.get(position));
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(data_dinaries.get(position).isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data_dinaries.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data_dinaries.size();
    }

    @Override
    public void click_food_type(Data_food_type data_food_type, int postion) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date,id_bill,id_employee,id_user;
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            id_employee = (TextView) itemView.findViewById(R.id.id_food_type);
            id_user = (TextView) itemView.findViewById(R.id.name_food_type);
            checkBox = (CheckBox)itemView.findViewById(R.id.check_food_type);

        }

        void bindData(Data_food_type data_tt) {
            id_user.setText(data_tt.getName());
            id_employee.setText(""+data_tt.getId_tpye());
        }

    }
    public void update_Bill(ArrayList<Data_food_type> todolist){
        this.data_dinaries = todolist;
        notifyDataSetChanged();
    }
}

