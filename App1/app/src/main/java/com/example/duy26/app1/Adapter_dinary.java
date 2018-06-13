package com.example.duy26.app1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_dinary extends RecyclerView.Adapter<Adapter_dinary.ViewHolder> implements Onitemclick123 {
    private ArrayList<Data_Dinary> data_dinaries;
    private Context context;
    private Onitemclick123 onitemclick;

    public Adapter_dinary(ArrayList<Data_Dinary> data_dinaries, Context context, Onitemclick123 onitemclick) {
        this.data_dinaries = data_dinaries;
        this.context = context;
        this.onitemclick = onitemclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dinary, parent, false);
        final Adapter_dinary.ViewHolder holder1 = new Adapter_dinary.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclick.onItem(data_dinaries.get(holder1.getAdapterPosition()),
                        holder1.getAdapterPosition());
            }
        });
        return holder1;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(data_dinaries.get(position));
    }

    @Override
    public int getItemCount() {
        return data_dinaries.size();
    }

    @Override
    public void onItem(Data_Dinary data_dinary, int post) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date,id_bill;
        private Onitemclick123 itemclick;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.dinary_date);
            id_bill = (TextView) itemView.findViewById(R.id.dinary_idbill);


        }

        void bindData(Data_Dinary data_tt) {
            date.setText(data_tt.getDate());
            id_bill.setText(String.valueOf(data_tt.getId_bill()));

        }

    }
}
