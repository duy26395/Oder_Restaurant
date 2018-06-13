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

public class Adapter_delete_IDfood extends RecyclerView.Adapter<Adapter_delete_IDfood.ViewHolder>{
    ArrayList<Data_Food_delete> data_billdetails_dinaries;
    Context context;

    public Adapter_delete_IDfood(ArrayList<Data_Food_delete> data_billdetails_dinaries, Context context) {
        this.data_billdetails_dinaries = data_billdetails_dinaries;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_delete_IDfood.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete_idfood, parent, false);
        return new Adapter_delete_IDfood.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_delete_IDfood.ViewHolder holder, int position) {
        holder.bindData(data_billdetails_dinaries.get(position));

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(data_billdetails_dinaries.get(position).isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data_billdetails_dinaries.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_billdetails_dinaries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView food,ID,gia;
        private CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            food = (TextView) itemView.findViewById(R.id.name_food_delete);
            gia= (TextView) itemView.findViewById(R.id.price_food_delete);
            checkBox = (CheckBox)itemView.findViewById(R.id.check_delete);



        }
        void bindData(Data_Food_delete data_tt) {
            food.setText(data_tt.getName().toString());
            gia.setText(data_tt.getPrice().toString());

        }
    }
    public void update_delete_food(ArrayList<Data_Food_delete> todolist){
        this.data_billdetails_dinaries = todolist;
        notifyDataSetChanged();
    }
}
