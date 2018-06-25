package com.example.duy26.app1.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.duy26.app1.R;

import java.util.ArrayList;

public class Adapter_BillofDetails_mn extends RecyclerView.Adapter<Adapter_BillofDetails_mn.ViewHolder> implements Click_BillofDetails {
    private ArrayList<Data_BillofDetails> data_dinaries;
    private Context context;
    private Click_BillofDetails onItemClick;

    Adapter_BillofDetails_mn(ArrayList<Data_BillofDetails> data_dinaries, Context context, Click_BillofDetails onItemClick) {
        this.data_dinaries = data_dinaries;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public Adapter_BillofDetails_mn.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_bill_mn, parent, false);
        final Adapter_BillofDetails_mn.ViewHolder holder1 = new Adapter_BillofDetails_mn.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemclick(data_dinaries.get(holder1.getAdapterPosition()),
                        holder1.getAdapterPosition());
            }
        });
        return holder1;

    }


    @Override
    public int getItemCount() {
        return data_dinaries.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_BillofDetails_mn.ViewHolder holder, int position) {
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
    public void itemclick(Data_BillofDetails data_billofDetails, int postion) {

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id,number,price;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_billdetais_ten_mn);
            number = itemView.findViewById(R.id.item_billdetails_number_mn);
            price = itemView.findViewById(R.id.item_billdetails_price_mn);
            checkBox = itemView.findViewById(R.id.item_billdetails_checkbox_mn);

        }
        void bindData(Data_BillofDetails data_tt) {

            id.setText(data_tt.getTen_food());
            price.setText(data_tt.getPrice());
            number.setText(data_tt.getNumber());
        }
    }
    public void update_Billdetails(ArrayList<Data_BillofDetails> todolist){
        this.data_dinaries = todolist;
        notifyDataSetChanged();
    }
}