package com.example.duy26.app1.Admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duy26.app1.R;

import java.util.ArrayList;

public class Adapter_delete_clickLong extends RecyclerView.Adapter<Adapter_delete_clickLong.ViewHolder> implements click_food{
    ArrayList<Data_delete_food_clicklong> data_billdetails_dinaries;
    Context context;
    private click_food clickFood;

    public Adapter_delete_clickLong(ArrayList<Data_delete_food_clicklong> data_billdetails_dinaries, Context context,click_food clickFood) {
        this.data_billdetails_dinaries = data_billdetails_dinaries;
        this.context = context;
        this.clickFood = clickFood;
    }

    @NonNull
    @Override
    public Adapter_delete_clickLong.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_food_mn, parent, false);
        final Adapter_delete_clickLong.ViewHolder holder1 = new Adapter_delete_clickLong.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFood.onclick(data_billdetails_dinaries.get(holder1.getAdapterPosition()),
                        holder1.getAdapterPosition());
            }
        });
        return holder1;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.txtname.setText(data_billdetails_dinaries.get(position).getName_food());
        String i = data_billdetails_dinaries.get(position).getImage();
        try {
            if (i.contains("http")) {
                Glide.with(context)
                        .load(i)
                        .into(holder.imageView);
            } else Glide.with(context)
                    .load(decodeBase64(i))
                    .into(holder.imageView);
        }catch (Exception e){}
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

    @Override
    public void onclick(Data_delete_food_clicklong data_delete_food_clicklong, int pos) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtname;
        private ImageView imageView;
        CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            txtname =  (TextView)itemView.findViewById(R.id.id_itemmon_mn);
            imageView = (ImageView)itemView.findViewById(R.id.id_imgitem_mn);
            checkBox = (CheckBox)itemView.findViewById(R.id.check_item_food_mn);



        }
    }
    public void update_delete_food(ArrayList<Data_delete_food_clicklong> todolist){
        this.data_billdetails_dinaries = todolist;
        notifyDataSetChanged();
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
