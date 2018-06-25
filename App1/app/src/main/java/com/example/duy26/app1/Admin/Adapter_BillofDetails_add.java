package com.example.duy26.app1.Admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duy26.app1.CustomItemClickListener;
import com.example.duy26.app1.Data;
import com.example.duy26.app1.Data_Food;
import com.example.duy26.app1.R;

import java.util.ArrayList;

public class Adapter_BillofDetails_add extends RecyclerView.Adapter<Adapter_BillofDetails_add.viewholder> implements Filterable,CustomItemClickListener {
    private ArrayList<Data_Food> data_foods;
    private ArrayList<Data_Food> fUserList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public Adapter_BillofDetails_add(ArrayList<Data_Food> arrayList, Context context,CustomItemClickListener customItemClickListener) {
        this.data_foods = arrayList;
        this.fUserList = arrayList;
        this.context = context;
        this.customItemClickListener =  customItemClickListener;
    }
    @NonNull
    @Override
    public Adapter_BillofDetails_add.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_food,parent,false);
        final Adapter_BillofDetails_add.viewholder holder1 = new Adapter_BillofDetails_add.viewholder(itemview);
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemClickListener.onClick(fUserList.get(holder1.getAdapterPosition()),
                        holder1.getAdapterPosition());
            }
        });
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.txtname.setText(fUserList.get(position).getName_food());
        String i = fUserList.get(position).getImage();
        try {
            if (i.contains("http")) {
                Glide.with(context)
                        .load(i)
                        .into(holder.imageView);
            } else Glide.with(context)
                    .load(decodeBase64(i))
                    .into(holder.imageView);
        }catch (Exception e){}
    }


    @Override
    public int getItemCount() {
        Log.e("DDDDDDDDDDDDDDiiiiiiiiiiiiiiii", String.valueOf(fUserList));
        return fUserList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String search = constraint.toString();
                if (search.isEmpty()) {
                    fUserList = data_foods;
                } else {
                    ArrayList<Data_Food> dataArrayList = new ArrayList<>();
                    for (Data_Food data_food : data_foods) {
                        if (data_food.getName_food().toLowerCase().contains(constraint)) {

                            dataArrayList.add(data_food);
                        }
                    }
                    fUserList = dataArrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = fUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                fUserList = (ArrayList<Data_Food> )results.values;
                notifyDataSetChanged();

            }
        };
    }

    @Override
    public void onItemClick(Data user, int position) {

    }

    @Override
    public void onClick(Data_Food data_food, int position) {

    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtname;
        ImageView imageView;
        public viewholder(View itemView) {
            super(itemView);

            txtname =  (TextView)itemView.findViewById(R.id.id_itemmon);
            imageView = (ImageView)itemView.findViewById(R.id.id_imgitem);

        }
    }
    public static Bitmap decodeBase64(String input)
    {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        byte[] decodedBytes = Base64.decode(input,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes,0,decodedBytes.length,options);
    }

}
