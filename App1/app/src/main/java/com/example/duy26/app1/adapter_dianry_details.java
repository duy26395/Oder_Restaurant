package com.example.duy26.app1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duy26.app1.Admin.Onitemclick;

import java.util.ArrayList;

public class adapter_dianry_details extends RecyclerView.Adapter<adapter_dianry_details.ViewHolder>{
    ArrayList<Data_billdetails_dinary> data_billdetails_dinaries;
    Context context;

    public adapter_dianry_details(ArrayList<Data_billdetails_dinary> data_billdetails_dinaries, Context context) {
        this.data_billdetails_dinaries = data_billdetails_dinaries;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dinary_item_details, parent, false);
        return new adapter_dianry_details.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(data_billdetails_dinaries.get(position));
    }

    @Override
    public int getItemCount() {
        return data_billdetails_dinaries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView food,sl,gia;
        private Onitemclick itemclick;
        public ViewHolder(View itemView) {
            super(itemView);
            food = (TextView) itemView.findViewById(R.id.item_dinarydetails_ten);
            sl = (TextView) itemView.findViewById(R.id.item_dinarydetails_sl);
            gia= (TextView) itemView.findViewById(R.id.item_dinarydetails_gia);



        }
        void bindData(Data_billdetails_dinary data_tt) {
            food.setText(data_tt.getTen().toString());
            sl.setText(data_tt.getSluong().toString());
            gia.setText(data_tt.getGia().toString());

        }
    }

}
