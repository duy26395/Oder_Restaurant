package com.example.duy26.app1.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.duy26.app1.Data_Dinary;
import com.example.duy26.app1.Onitemclick123;
import com.example.duy26.app1.R;

import java.util.ArrayList;



public class Adapter_bill_mn extends RecyclerView.Adapter<Adapter_bill_mn.ViewHolder> implements Onitemclick123 {
    private ArrayList<Data_mn_BILL_details> data_dinaries;
    private Context context;
    private Click_data_bill_details onItemClick;

    public Adapter_bill_mn(ArrayList<Data_mn_BILL_details> data_dinaries, Context context, Click_data_bill_details onItemClick) {
        this.data_dinaries = data_dinaries;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public Adapter_bill_mn.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mn_bill, parent, false);
        final Adapter_bill_mn.ViewHolder holder1 = new Adapter_bill_mn.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.ItemClick(data_dinaries.get(holder1.getAdapterPosition()),
                        holder1.getAdapterPosition());
            }
        });
        return holder1;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
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
    public void onItem(Data_Dinary data_dinary, int post) {

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date,id_bill,id_employee,id_user;
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.manager_bill_date);
//            id_bill = (TextView) itemView.findViewById(R.id.manager_bill_id);
            id_employee = (TextView) itemView.findViewById(R.id.manager_bill_idemployess);
            id_user = (TextView) itemView.findViewById(R.id.manager_bill_iduser);
            checkBox = (CheckBox)itemView.findViewById(R.id.manager_bill_checkbox);

        }

        @SuppressLint("SetTextI18n")
        void bindData(Data_mn_BILL_details data_tt) {
            date.setText(""+data_tt.getDate().trim());
            id_user.setText(""+data_tt.getTenuser().trim());
            id_employee.setText(""+data_tt.getTennv().trim());
        }

    }
    public void update_Bill(ArrayList<Data_mn_BILL_details> todolist){
        this.data_dinaries = todolist;
        notifyDataSetChanged();
    }
}
